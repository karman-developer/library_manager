// -- user1のパスワード → pass_user1
// -- user2のパスワード → pass_user2
	
public interface LogRepository extends JpaRepository<Log, Integer>
| ジェネリクス | 意味                    |
| ------ | --------------------- |
| `T`    | 対象となるエンティティ型（例：`Log`） |
| `ID`   | 主キーの型（例：`Integer`）    |
	