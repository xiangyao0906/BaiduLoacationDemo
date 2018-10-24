package fruits.sancai.com.baiduloacationdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        findViewById<LinearLayout>(R.id.selfPermission).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }
        findViewById<LinearLayout>(R.id.thirdPermission).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    PermissionWithThirdActivity::class.java
                )
            )
        }
    }
}
