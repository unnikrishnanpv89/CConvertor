object App {
    const val compileSdkVersion = 34
    const val minSdkVersion = 26
    const val targetSdkVersion = 34
    const val APPLICATION_ID = "com.open.exchange.cconverter"

    object Version {
        private const val MAJOR = 1
        private const val MINOR = 0
        private const val PATCH = 0
        private const val BUILD = 0

        const val CODE = MAJOR * 10_000 + MINOR * 1_000 + PATCH * 100 + BUILD
        const val NAME = "$MAJOR.$MINOR.$PATCH"
    }
}