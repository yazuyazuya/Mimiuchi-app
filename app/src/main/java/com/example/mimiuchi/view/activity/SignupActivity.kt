package com.example.mimiuchi.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mimiuchi.R
import com.example.mimiuchi.model.User
import com.example.mimiuchi.presenter.activity.SignupContract
import com.example.mimiuchi.presenter.activity.SignupPresenter
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() ,SignupContract.View{


    override lateinit var presenter: SignupContract.Presenter


    val genderItems = arrayListOf("性別", "男性", "女性", "特に区別して欲しくない")
    val ageItems = arrayListOf("年齢", "10代以下", "20代", "30代", "40代", "50代", "60代", "70代以上")

    var gender : String = ""
    var age : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = "新規会員登録"
        presenter = SignupPresenter(this)

        // 左上の戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // adapterを設定
        val genderAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            genderItems
        )

        val ageAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            ageItems
        )

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // spinnerにadapterをセット
        genderSpinner.adapter = genderAdapter
        ageSpinner.adapter = ageAdapter

        // リスナーを登録
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //　アイテムが選択された時
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinnerParent = parent as Spinner
                gender = spinnerParent.selectedItem as String
                Log.d("gender", gender)
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        }

        ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //　アイテムが選択された時
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinnerParent = parent as Spinner
                age = spinnerParent.selectedItem as String
                Log.d("age", age)
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        }

        signupButton.setOnClickListener {
            // チュートリアル画面へと遷移
//            val intent = Intent(this, TutorialActivity::class.java)

           presenter.start()
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

    }

    //アクションバーの[戻る]処理
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun showError() {

        err.text = "エラー：アカウントを作成できません"
    }

    override fun tutorial() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("ACT_KEY", "SignUp")
        startActivity(intent)
    }

    override fun User(): User {

        when(gender) {
            "男性" -> gender = "man"
            "女性" -> gender = "woman"
            "特に区別して欲しくない" -> ""

        }
        var Age = 0
        when(age){
            "10代以下"->Age=10
            "20代" -> Age=20
            "30代" -> Age=30
            "40代" -> Age=40
            "50代" -> Age=50
            "60代" -> Age=60
            "70代以上" -> Age=70
        }

        val user = User(nUseridEditText.text.toString(),nPasswordEditText.text.toString(),gender,Age)
        return user
    }
}
