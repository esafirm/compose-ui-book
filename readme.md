# Compose UI Book

[![](https://jitpack.io/v/esafirm/android-ui-book.svg)](https://jitpack.io/#esafirm/android-ui-book)

Simple and extensible UI component explorer for Android

<p float="middle">
	<img src="https://raw.githubusercontent.com/esafirm/android-ui-book/main/.github/art/ss1.png" height="420" width="244"/>
	<img src="https://raw.githubusercontent.com/esafirm/android-ui-book/main/.github/art/ss2.png" height="420" width="244"/>
	<img src="https://raw.githubusercontent.com/esafirm/android-ui-book/main/.github/art/ss3.png" height="420" width="244"/>
</p>

<details>
	<summary>You can check the demo video here</summary>
	<a href="https://www.youtube.com/watch?v=aB2cBjLuYHA">
		<img src="https://img.youtube.com/vi/aB2cBjLuYHA/0.jpg" />
	</a>
</details>

## Getting Started


### Setup Module

Usually the UI component explorer is separated from the main app. So the first thing you should do is to create empty module
with `com.android.application` plugin. After that:

In your root `build.gradle`

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

And in your project `build.gradle`

```groovy
dependencies {
  implementation 'com.github.esafirm.android-ui-book:browser:x.y.z'
  kapt 'com.github.esafirm.android-ui-book:processors:x.y.z'	
}
```

change `x.y.z` to version in the [release page](https://github.com/esafirm/android-ui-book/releases)

### Create First Book

To create book simple creat an extension function that extend `BookHost`, return `View` and give the function `@UIBook` annotation.
In this case I will create a Kotlin file with name `Catalogue.kt` and then create this function:

```kotlin
@UIBook(name = "TextView")
fun BookHost.createTextView(text: String): TextView {
  /**
  * This will draw text
  */
  return TextView(context).apply {
    this.text = text
    setTextColor(Color.RED)
  }
}
```

That's it now run the new module that you just created.

## Advanced

For now, you can check `:sample` module for complete features.

## Development

To develop the project, you need to use Android Studio Dolphin above.

It have some strange issue with IntelliJ Idea 2022.3 EAP.

## Support

<a href='https://ko-fi.com/M4M41RRE0' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://cdn.ko-fi.com/cdn/kofi4.png?v=2' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

## License

Esa @ MIT
