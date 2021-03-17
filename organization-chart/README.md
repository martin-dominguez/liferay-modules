[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
![GitHub top language][top-lenguage-shield]
[![LinkedIn][linkedin-shield]][linkedin-url]

# About The Module

This module provides an organization chart based on [Google Organization Chart](https://developers.google.com/chart/interactive/docs/gallery/orgchart). The Look and Feel has been improved using Bootstrap and the interactions with users have been also improved using JQuery. 

![Overview][overview-img]
![Detail][detail-img]
![Organization Chart Config][config-img]

The module has the following features:

 * AutoPopulation from System's users
 * Tree View: Picture, Name and position are shown in the tree
 * Profile detail on click with extra info: email, local time and last connection
 * Social features: Followers are shown and you can follow/unfollow users if you're logged into Liferay
 * Exclude users: You can do it by don't filling the *Job Title* field
 * Configure manager's field name

## Built With

This Module has been build using the following software:
* [Google Organization Chart](https://developers.google.com/chart/interactive/docs/gallery/orgchart)
* [Bootstrap 4](https://getbootstrap.com)
* [JQuery](https://jquery.com)
* [Lexicon](https://liferay.design/lexicon/)

# Getting Started

This module has been created Google Chart libraries and JQuery, so both must be supported in your systems.

## Prerequisites

* This module is compatible with **Liferay 7.3**
* It needs **JQuery** to be enabled in Liferay: * Control Panel > System Settings > Third Party > JQuery > Enable (Checkbox)*

## Build it
` $ ./gradlew build `
The jar file will be in `build/libs/com.liferay.semodules.organizationChart-{version}.jar`.

These are the relevant configuration properties used to build it locally:
- **gradle.properties**:
```
liferay.workspace.product = dxp-7.3-ga1
```

- **settings.gradle**
```
dependencies {
	classpath group: "com.liferay", name: "com.liferay.gradle.plugins.workspace", version: "3.0.11"
	classpath group: "net.saliman", name: "gradle-properties-plugin", version: "1.4.6"
}
```

1. Create a **Custom Field** to store the user's manager: *Contorl Panel > Custom Fields > User*
2. You can call it as you prefer, but the default name es "Manager"
3. **Configure the module** setting the field that you're going to use to store the user's manager: *Control Panel > System Settings > Third Party >  Organization Chart*
4. **Add the "Organization Chart" widget** into a Liferay Page. You'll find it under the *"Intranet"* category
5. Fill **"Job Title"** in the users profile. If it ins't filled, the **user will not be shown** in the Organization Chart
6. Fill alsa the **manager's field** (*Manager* by default) with the **screen name** of the manager. If it isn't filled, the user will be **shown in the top of the tree**.
	

# TODO List

* Configure visual options: A configuration page skeleton has been created to add visual configuration, but it's empty.

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/martin-dominguez/liferay-modules.svg
[contributors-url]: https://github.com/martin-dominguez/liferay-modules/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/martin-dominguez/liferay-modules.svg
[forks-url]: https://github.com/martin-dominguez/liferay-modules/network/members
[stars-shield]: https://img.shields.io/github/stars/martin-dominguez/liferay-modules.svg
[stars-url]: https://github.com/martin-dominguez/liferay-modules/stargazers
[issues-shield]: https://img.shields.io/github/issues/martin-dominguez/liferay-modules.svg
[issues-url]: https://github.com/martin-dominguez/liferay-modules/issues
[top-lenguage-shield]: https://img.shields.io/github/languages/top/martin-dominguez/liferay-modules
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/-martin-dominguez/
[overview-img]: doc-images/orgchart1.png
[detail-img]: doc-images/orgchart2.png
[config-img]: doc-images/orgchart3.png
