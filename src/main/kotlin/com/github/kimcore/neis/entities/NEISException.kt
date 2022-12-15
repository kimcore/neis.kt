package com.github.kimcore.neis.entities

import kotlinx.serialization.json.JsonObject

class NEISException(message: String, json: JsonObject?, cause: Throwable) : Throwable(message, cause)