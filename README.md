# IndicatorSeekBar
A seekbar with custom indicator which supports RTL layout too.

Add it in your root build.gradle at the end of repositories:

  	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
Add the dependency:

  	dependencies {
	        implementation 'com.github.GDMistry:IndicatorSeekBar:Tag'
	}
  
  
Usage :

        //Use like this
        binding.indicatorSeeker.setMax(200)
        binding.indicatorSeeker.makeRTL()

        binding.indicatorSeeker.setIndicatorTextAttribs(12f, android.R.color.holo_red_dark, null)

        binding.indicatorSeeker.callback = object : IndicatorSeeker.Callback {
            override fun onProgressChanged(seeker: SeekBar?, progress: Int, fromUser: Boolean) {
                //Your task
            }
        }
        
Preview :

![image](https://user-images.githubusercontent.com/66417665/114538213-be6cbe80-9c70-11eb-93a1-dd9ec9245580.png)
