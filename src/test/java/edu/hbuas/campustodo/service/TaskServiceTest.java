package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link TaskService} 的基线测试。
 *
 * <p>起始版本至少覆盖两类场景：
 * <ol>
 *   <li>新增任务：成功创建并分配 id、标题正确、初始 completed 为 false。</li>
 *   <li>空标题校验：null、空串、纯空白串均应抛出 {@link IllegalArgumentException}。</li>
 * </ol>
 *
 * <p>后续 Issue 中，开发者应在本类或新增测试类中补充对应功能的测试，
 * 并保持基线测试持续通过。
 *
 * @author CampusTodo Lab
 */
class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        // 每个测试方法使用全新的 service，避免相互影响
        service = new TaskService();
    }

    // ================================================================
    // 新增任务
    // ================================================================

    @Test
    @DisplayName("addTask：合法标题应返回带 id 与标题的任务，且 completed 为 false")
    void addTask_withValidTitle_returnsTaskWithIdAndTitle() {
        Task task = service.addTask("Review pull request");

        assertNotNull(task, "addTask 不应返回 null");
        assertTrue(task.getId() > 0, "新增任务的 id 应为正数");
        assertEquals("Review pull request", task.getTitle(), "标题应与传入值一致");
        assertFalse(task.isCompleted(), "新建任务默认未完成");
    }

    @Test
    @DisplayName("addTask：连续新增任务时 id 应自增")
    void addTask_incrementsIdSequentially() {
        Task first = service.addTask("First task");
        Task second = service.addTask("Second task");

        assertEquals(first.getId() + 1, second.getId(), "第二个任务的 id 应比第一个大 1");
    }

    @Test
    @DisplayName("addTask：标题前后空白应被 trim，存储为干净标题")
    void addTask_trimsSurroundingWhitespace() {
        Task task = service.addTask("  Submit report  ");

        assertEquals("Submit report", task.getTitle(), "标题前后空白应被去除");
    }

    // ================================================================
    // 空标题校验
    // ================================================================

    @Test
    @DisplayName("addTask：null 标题应抛出 IllegalArgumentException")
    void addTask_withNullTitle_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addTask(null),
                "null 标题应被拒绝");
    }

    @Test
    @DisplayName("addTask：空字符串标题应抛出 IllegalArgumentException")
    void addTask_withEmptyTitle_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addTask(""),
                "空字符串标题应被拒绝");
    }

    @Test
    @DisplayName("addTask：纯空白标题应抛出 IllegalArgumentException")
    void addTask_withBlankTitle_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addTask("   "),
                "纯空白标题应被拒绝");
    }

    // ================================================================
    // 列出任务
    // ================================================================

    @Test
    @DisplayName("listAll：无任务时应返回空列表")
    void listAll_whenNoTasks_returnsEmptyList() {
        List<Task> all = service.listAll();

        assertNotNull(all, "listAll 不应返回 null");
        assertTrue(all.isEmpty(), "初始状态下任务列表应为空");
    }

    @Test
    @DisplayName("listAll：新增任务后应按插入顺序返回全部任务")
    void listAll_afterAddingTasks_returnsAllTasksInOrder() {
        service.addTask("Task A");
        service.addTask("Task B");
        service.addTask("Task C");

        List<Task> all = service.listAll();

        assertEquals(3, all.size(), "应返回 3 个任务");
        assertEquals("Task A", all.get(0).getTitle());
        assertEquals("Task B", all.get(1).getTitle());
        assertEquals("Task C", all.get(2).getTitle());
    }

    @Test
    @DisplayName("listAll：返回的列表不可修改")
    void listAll_returnsUnmodifiableList() {
        service.addTask("Task A");

        List<Task> all = service.listAll();

        assertThrows(UnsupportedOperationException.class,
                () -> all.add(new Task(99, "Injected")),
                "返回的列表应不可修改，防止外部绕过 addTask 改变内部状态");
    }
}
