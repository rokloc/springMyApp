〇認証フローのイメージ

ユーザーがログインフォームに ID とパスワードを入力
Spring Security が UserDetailsService にユーザー名を渡す
UserDetailsService が DB からユーザー情報を取得
パスワードのハッシュ値をチェックして認証
認証成功 → セッション作成・アクセス許可


[ログインフォーム入力]
   username + password
          ↓
[AuthenticationManager] ← Spring Security
          ↓
loadUserByUsername(username) ← MyUserDetailsService
          ↓
[DB] → UserEntity取得
          ↓
[UserDetails] ← User.builder()で生成
          ↓
[パスワード照合] ← passwordEncoder.matches(raw, hash)
          ↓
認証成功 → セッション作成 / 認可判定




[ユーザー登録フォーム入力]
      ↓ POST /register
[RegisterController]
      ↓ passwordEncoder.encode() → ハッシュ化
[DB保存] → UserEntity
      ↓
[ログインフォーム] で認証
      ↓
MyUserDetailsService.loadUserByUsername() が DB を参照
      ↓
UserDetails を生成 → パスワード照合 → 認証成功
