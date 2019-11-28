package com.donald.firebasedemo

class UserModel {

    var uid: String? = null
    var names: String? = null
    var email: String? = null
    var phone: String? = null


    constructor(){}

    constructor(uid: String?, names: String?, email: String?, phone: String?) {
        this.uid = uid
        this.names = names
        this.email = email
        this.phone = phone
    }

    constructor(names: String?, email: String?, phone: String?) {
        this.names = names
        this.email = email
        this.phone = phone
    }




}