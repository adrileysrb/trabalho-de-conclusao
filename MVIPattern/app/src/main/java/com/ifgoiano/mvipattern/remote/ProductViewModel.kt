package com.ifgoiano.mvipattern.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ifgoiano.mvipattern.model.Data

class ProductViewModel: ViewModel() {

    private var db = Firebase.firestore
    private val products = "products"

    val createLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val getListLiveData: MutableLiveData<List<Data>> by lazy {
        MutableLiveData<List<Data>>()
    }

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun create(product: Data) {
        val docRef = db.collection(products)
        docRef.add(product.toMap()).addOnSuccessListener {
            createLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("create", it.localizedMessage!!)
            createLiveData.postValue(false)
        }
    }

    fun update(product: Data) {
        val docRef = db.collection(products)
        docRef.document(product.id!!).update(product.toMap()).addOnSuccessListener {
            updateLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("update", it.localizedMessage!!)
            updateLiveData.postValue(false)
        }
    }

    fun delete(id: String) {
        val docRef = db.collection(products)
        docRef.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

    fun getList() {
        val docRef = db.collection(products)
        docRef.get().addOnSuccessListener {
            val products = ArrayList<Data>()
            for (item in it.documents) {
                val product = Data()
                product.id = item.id
                product.name = item.data!!["name"] as String?
                products.add(product)
            }

            getListLiveData.postValue(products)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }
}