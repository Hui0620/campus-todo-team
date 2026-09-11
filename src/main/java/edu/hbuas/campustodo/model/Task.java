package edu.hbuas.campustodo.model;

/**
 * 任务模型。
 *
 * <p>起始版本（v0.1.0）仅包含三个字段：
 * <ul>
 *   <li>{@code id} —— 任务唯一编号，由 {@code TaskService} 在新增时自动分配。</li>
 *   <li>{@code title} —— 任务标题，不允许为 {@code null} 或空白。</li>
 *   <li>{@code completed} —— 是否已完成，默认为 {@code false}。</li>
 * </ul>
 *
 * <p>后续迭代（Issue #1）将在此类中增加 {@code Priority} 字段；
 * Issue #2 将通过 {@code TaskService#completeTask(long)} 修改 {@code completed}。
 * 学生在扩展本类时应保持现有字段与构造方法签名不变，避免破坏基线测试。
 *
 * @author CampusTodo Lab
 */
public class Task {
    // ============ 新增：优先级枚举 ============
    public enum Priority {
        HIGH, MEDIUM, LOW
    }
    // ==========================================

    private long id;
    private String title;
    private boolean completed;
    // ============ 新增：优先级字段 ============
    private Priority priority;
    // =========================================

    /**
     * 默认构造方法，供框架或测试工具使用。
     */
    public Task() {
    }

    /**
     * 通过 id 与 title 构造任务，{@code completed} 默认为 {@code false}。
     *
     * @param id    任务编号，必须大于 0
     * @param title 任务标题，不允许为 {@code null} 或空白
     */
    public Task(long id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
        // ============ 新增：默认优先级 MEDIUM ============
        this.priority = Priority.MEDIUM;
        // ================================================
    }

    // ============ 新增：带优先级的构造器 ============
    public Task(long id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.completed = false;
        this.priority = priority;
    }
    // ================================================

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // ============ 新增 priority getter/setter ============
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    // =====================================================

    @Override
    public String toString() {
        // toString追加priority，不破坏原有字段
        return "Task{id=" + id + ", title='" + title + "', completed=" + completed + ", priority=" + priority + "}";
    }
}
