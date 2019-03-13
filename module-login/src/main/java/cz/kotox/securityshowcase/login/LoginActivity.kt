package cz.kotox.securityshowcase.login

import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import cz.kotox.securityshowcase.core.arch.BaseActivity
import cz.kotox.securityshowcase.core.database.preferences.PreferencesCommon
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivity() {

	@Inject
	lateinit var preferencesCore: PreferencesCommon

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)

		val host: NavHostFragment = supportFragmentManager
			.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment? ?: return

		// Set up Action Bar
		val navController = host.navController
		setupActionBar(navController)

		navController.addOnDestinationChangedListener { _, destination, _ ->
			val dest: String = try {
				resources.getResourceName(destination.id)
			} catch (e: Resources.NotFoundException) {
				Integer.toString(destination.id)
			}

			Timber.d("Navigated to %s", dest)
		}

		Timber.d(">>>${preferencesCore.jwtToken}")
	}

	private fun setupActionBar(navController: NavController) {
		NavigationUI.setupActionBarWithNavController(this, navController)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Have the NavHelper look for an action or destination matching the menu
		// item id and navigate there if found.
		// Otherwise, bubble up to the parent.
		return NavigationUI.onNavDestinationSelected(item,
			Navigation.findNavController(this, R.id.login_nav_host_fragment))
			|| super.onOptionsItemSelected(item)
	}

}