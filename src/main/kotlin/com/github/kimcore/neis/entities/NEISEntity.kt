package com.github.kimcore.neis.entities

import kotlinx.serialization.json.JsonObject

abstract class NEISEntity {
    lateinit var raw: JsonObject
        internal set
}