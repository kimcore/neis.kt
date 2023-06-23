package com.github.kimcore.neis.entities

import com.github.kimcore.neis.NeisAPI
import kotlinx.serialization.json.JsonObject

abstract class NEISEntity {
    lateinit var neis: NeisAPI
        internal set
    lateinit var raw: JsonObject
        internal set
}