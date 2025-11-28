conda activate myenv

set ANDROID_HOME=C:\\Users\\WGJ4WX\\AppData\\Local\\Android\\Sdk
set ANDROID_NDK_ROOT=C:\\Users\\WGJ4WX\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393

scons platform=android target=template_debug arch=arm64v8
