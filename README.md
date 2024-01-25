
# bookmap
### 本の進捗を管理するアプリです。
※こちらはMySQLを使ってみたいという製作者の欲だけで作られたものなので、利用するためにはいくつかの準備が必要です。  
　準備を必要としない同様のアプリもあるので、そちらの利用を推奨します。  
　https://github.com/kendama5ko/bookmap_ver_Json
　


## 紹介
このアプリのウィンドウは以下の2つだけです。
### 選択された本の情報を表示（メイン画面）
![MainWindow](https://github.com/kendama5ko/bookmap/assets/146686157/749f2619-3910-4610-88ab-d5ac95a59bbb)
<br>

### 全ての本の管理（本の追加・削除等）（サブ画面）
![ManageBooksWindow](https://github.com/kendama5ko/bookmap/assets/146686157/515fee1b-fc14-45f2-83cb-d6b25174c1f9)

## 準備
本アプリではMySQLとの接続を必要とします。  
そのために次の2つの準備を事前にお願いします。  
1. mysql.propertiesというファイルを追加（プログラムとMySQLを接続する設定ファイル）
2. createDatabase.sqlを実行（MySQLにbookmapというデータベースを追加し、必要なテーブルを作成）

### 1. MySQLとの接続 
メモ帳やテキストエディタを開き、次のように記述してください。  
```
# MySQL settings
URL=jdbc:mysql://localhost:3306/bookmap
USER=root
PASS=9999
```
USER,PASSについてはお持ちのMySQLのユーザーアカウントを入力してください。  
上の例ではユーザー名がroot、パスワードが9999です。  
これを、mysql.properties というファイル名で保存し、bookmapフォルダ内に追加してください。
### 2. データベースの作成
次に、bookmapフォルダ内にあるcreateDatabase.sqlファイルを実行してください。  
<details><summary><strong>sqlファイルの実行方法</strong></summary>
 
Windowsの場合はコマンドプロンプト、Mac/Linuxの場合はターミナルを起動して次のコマンドを実行してください。  
#### MySQLにログインしている場合  
```
source /Users/ユーザー名/Documents/bookmap/createDatabase.sql
```

#### MySQLにログインしていない場合  
```
mysql -u ユーザー名 -p /Users/ユーザー名/Documents/bookmap/createDatabase.sql
```
ファイルの場所については環境に合わせて変更をお願いします。  
</details>

<br>

## 使い方
![discription](https://github.com/kendama5ko/bookmap_ver_Json/assets/146686157/8e83128a-ecdd-4692-b254-f36d560db2cd)

①管理ボタン  
②タイトル、著者、ジャンル、ページ数を入力して追加ボタンを押す[^1]  
③右上のリストから追加した本を選択  
④読んだページ数を入力  
⑤記録する  
[^1]: 本の追加には必ずページ数を入力してください。  



