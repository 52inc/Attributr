#Attributr


## Summary

An Android library for easily displaying a list of license attributions for 3rd party libraries in your applications.


## Usage

1.	#### Configuration File

		<Configuration>
	
		    <Library>
		        <Name>ButterKnife</Name>
		        <Author>Jake Wharton</Author>
		        <Source>http://jakewharton.github.io/butterknife/</Source>
		        <License>
		            Copyright © 2013 Jake Wharton\n\nLicensed under the Apache License, Version 2.0 (the 'License'); you may not use this file except in compliance with the License. You may obtain a copy of the License at\n\nhttp://www.apache.org/licenses/LICENSE-2.0\n\nUnless required by applicable law or agreed to in writing, software distributed under the License is distributed on an 'AS IS' BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
		        </License>
		    </Library>
	    
	    </Configuration>
	    
	    
	This is an example of the format needed to use Attributr. 

2. #### Code usage

		Attributr.openLicenseActivity(getActivity(),
                            "Some activity title",
                            R.raw.license_config,
                            R.drawable.ic_action_launcher,
                            R.style.Theme_Custom);

	Just call this to launch the attribution license activity to display your previous configuration in a beautiful card format.
	
3. #### Customization

	Just call ````Attributr.parseConfiguration(ctx, R.raw.license_config);```` to get a list of ````Library```` objects to use in your own manner. This way you can create your own Adapter to use in whatever view model you wish to display.


## Planned Features

*	Easier 'License' configuration
	* 	Specifying a license type via attribute (i.e. ````type="apache2|mit|gplv3|etc..."````) and supply the appropriate information to place into the template license string.
	
*	Style configurations
	*   Supply the developer with a configuration class that they can use to customize the styling of the license card views and list.
	
	
## Including in your project

Just add this line:

	compile 'co.52inc:attributr:+'
	
In your project's build.gradle file

## Author

Drew Heavner ([r0adkll](http://r0adkll.net)) @ [52inc](http://www.52inc.co) 
