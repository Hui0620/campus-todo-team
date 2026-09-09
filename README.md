# CampusTodo

CampusTodo 是一个控制台版校园任务管理器，用于《软件工程综合实践》课程中
**"基于 GitHub Flow 的团队协同开发"**实验。

本仓库为起始项目（starter），仅包含基线功能。团队成员需按照 Issue 列表，
通过功能分支、Pull Request 与 CI 完成一次小型迭代。

---

## 当前版本

当前版本：支持新增任务和列出任务。

> ⚠️ **请勿随意修改上一行内容**。该固定句将在后续合并冲突练习中使用，
> 开发者 A 与开发者 B 会分别修改这一行，用于练习同一行文本冲突的处理。

---

## 项目结构

```
campus-todo/
├─ pom.xml
├─ README.md
├─ .gitignore
├─ src/main/java/edu/hbuas/campustodo/
│ ├─ model/Task.java          # 任务模型：id / title / completed
│ └─ service/TaskService.java  # 任务服务：addTask / listAll
└─ src/test/java/edu/hbuas/campustodo/service/
  └─ TaskServiceTest.java     # 基线测试
```

## 起始版本功能

| 模块 | 方法 | 说明 |
|------|------|------|
| `TaskService` | `addTask(String title)` | 新增任务，自动分配自增 id；标题为空或空白时抛出 `IllegalArgumentException` |
| `TaskService` | `listAll()` | 返回当前所有任务的只读视图 |
| `Task` | `id` / `title` / `completed` | 任务模型字段，`completed` 默认为 `false` |

## 环境要求

- JDK 17（终端执行 `java -version` 输出主版本号 17）
- Maven 3.6+（可使用 IntelliJ 内置 Maven）
- Git 2.x
- IntelliJ IDEA（已在 Settings → Version Control → Git 中完成 Test）

## 构建与测试

```bash
# 编译
mvn -q compile

# 运行测试
mvn test

# 完整验证（CI 将使用此命令）
mvn -B verify
```

在 IntelliJ IDEA 中打开本目录，等待 Maven 同步完成后，
可在 `TaskServiceTest` 上右键 → Run 运行基线测试。

## 协作流程（GitHub Flow）

```
Issue → 建分支 → 小步提交 → Push → Draft Pull Request
      → CI → Review → 修改 → Approve → Merge → 同步 main → 删除分支
```

- `main` 只保存可运行、测试通过的版本，禁止直接提交日常开发。
- 分支命名：`feature/<issue号>-<简短主题>`，例如 `feature/1-priority-filter`。
- 提交信息：`<type>: <动词开头的说明>`，type 可用 `feat`、`test`、`docs`、`fix`、`ci`、`chore`。
- 每个功能分支只解决一个 Issue；PR 描述必须使用 `Closes #编号` 建立关联。
- 合并前必须通过自动化测试、解决全部评审意见，并取得至少 1 人 Approve。
- 禁止使用 `git push --force`；出现错误时优先新增修复提交或使用 `git revert`。

## Git 首次配置

```bash
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
git config --global --list
```

若使用 GitHub 隐私邮箱，请在 GitHub → Settings → Emails 查看 noreply 地址后再配置。

## 后续迭代任务（由教师以 Issue 形式下发）

| Issue | 负责人 | 功能说明 | 验收标准 |
|-------|--------|----------|----------|
| #1 Priority filter | 开发者 A | 增加 HIGH/MEDIUM/LOW；支持按优先级筛选 | 空结果返回空列表；默认 MEDIUM；测试覆盖 |
| #2 Complete task | 开发者 B | 按编号完成任务；重复完成应报错 | 不存在编号报错；重复完成报错；测试覆盖 |
| #3 CI and guide | 质量负责人 Q | 增加 Maven CI、PR 模板和 README 指南 | PR/main 触发；mvn verify 通过；模板可见 |
