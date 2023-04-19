package com.example.sirus20.message.model

/*class MessageModel {
    var textMessage: String? = ""
    var textID: String? = ""

    constructor() {

    }

    constructor(message: String?, id: String?) {
        this.textMessage = message
        this.textID
    }
}*/

data class MessageModel(
    var textMessage: String? = "",
    var textID: String? = "",
    var time : String?=""
)