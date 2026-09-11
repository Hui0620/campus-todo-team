package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 任务服务，负责管理任务的生命周期。
 *
 * <p>起始版本（v0.1.0）仅提供两个方法：
 * <ul>
 *   <li>{@link #addTask(String)} —— 新增任务，自动分配自增 id，并校验标题非空。</li>
 *   <li>{@link #listAll()} —— 返回当前所有任务的只读视图。</li>
 * </ul>
 *
 * <p>后续迭代将在此类中扩展：
 * <ul>
 *   <li>Issue #1：{@code filterByPriority(Priority priority)}。</li>
 *   <li>Issue #2：{@link #completeTask(long id)}，含重复完成校验。</li>
 * </ul>
 *
 * <p>本类不是线程安全的；实验场景为单线程控制台程序，无需加锁。
 *
 * @author CampusTodo Lab
 */
public class TaskService {

    /** 内部任务存储，按插入顺序保留。 */
    private final List<Task> tasks = new ArrayList<>();

    /** 下一个可分配的任务 id，从 1 开始自增。 */
    private long nextId = 1;

    /**
     * 新增一个任务。
     *
     * <p>标题为 {@code null}、空字符串或仅含空白字符时，抛出
     * {@link IllegalArgumentException}，以保证任务标题始终有意义。
     *
     * @param title 任务标题，非空且非空白
     * @return 已创建并分配 id 的任务对象
     * @throws IllegalArgumentException 当 title 为 {@code null} 或空白时
     */
    public Task addTask(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title must not be null or blank.");
        }
        Task task = new Task(nextId++, title.trim());
        tasks.add(task);
        return task;
    }

    /**
     * 返回当前所有任务的只读视图。
     *
     * <p>返回的列表不可修改，调用方对返回值的任何写操作都会抛出
     * {@link UnsupportedOperationException}，从而避免外部代码绕过
     * {@code addTask} 直接修改内部状态。
     *
     * @return 不可修改的任务列表，按插入顺序排列；无任务时返回空列表
     */
    public List<Task> listAll() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * 根据任务id查找任务
     * @param id 任务id
     * @return 找到返回Task，找不到返回null
     */
    private Task getById(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    /**
     * 将指定id任务标记为已完成，包含重复完成校验。
     * <p>
     * 三种情况：
     * <ol>
     *     <li>不存在该id任务 → 返回false</li>
     *     <li>任务已经是完成状态 → 返回false（不重复修改）</li>
     *     <li>任务未完成 → 修改状态，返回true</li>
     * </ol>
     *
     * @param id 任务编号
     * @return 操作是否成功
     */
    public boolean completeTask(long id) {
        Task task = getById(id);
        if (task == null) {
            return false;
        }
        if (task.isCompleted()) {
            return false;
        }
        task.setCompleted(true);
        return true;
    }
}
