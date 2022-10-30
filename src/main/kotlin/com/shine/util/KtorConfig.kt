package com.shine.util

//使用jackson必须有无参构造函数，kotlin data class 不自动生成无参构造函数，必须手动指定

data class KtorConfig(
    val database: DBConfig,
    val jwt_user: JWTConfig
) {
    constructor() : this(DBConfig(), JWTConfig())
}


data class DBConfig(
    val driver: String,
    val url: String,
    val user: String,
    val password: String
) {
    constructor() : this("", "", "", "")
}


data class JWTConfig(
    val validityInMs: Int,
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String
) {
    constructor() : this(60000, "", "", "", "")
}
