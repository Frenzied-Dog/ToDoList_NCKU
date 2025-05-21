# 專案名稱

就 **簡單的待辦事項APP**

## 需求

- Java 22
- Maven >= 3.9.9

## 安裝與執行

### 1. 克隆此專案：
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```

### 2. 安裝依賴項並編譯：
   ```bash
   mvn clean
   mvn install
   ```

### 3. 執行專案：
   #### 1. cli版本
   ```bash
   java -jar ./target/todo_list-1.0-SNAPSHOT.jar --cli
   ```   

   #### 2. gui版本 (正在製作!)
   ```bash
   java -jar ./target/todo_list-1.0-SNAPSHOT.jar
   ```

## 實作功能

- 新增/刪除分類
- 新增/刪除待辦事項
- 修改待辦事項/分類
- 顯示所有待辦事項

## 待實現功能

- GUI實作

## 依賴項目

此專案依賴以下項目：

- **Java 22**: 用於執行應用程式的主要編程語言。
- **Maven**: 用於構建和管理專案的工具。版本：`>= 3.9.9`。
- **JavaFX**: 用於實現GUI的框架（待實現）。版本：`21.0.7`。
- **MySQL-connector**: 用於資料庫操作的套件。版本：`9.3.0`。
- **Gson**: 用於處理JSON資料的套件。版本：`2.10.1`。
- **FxGson**: 用於在JavaFX中處理JSON資料的擴展套件。版本：`5.0.0`。

## 作者

- E24116128 辜勤翰
- E24119029 許彥喬
- F74124040 顏孜芸