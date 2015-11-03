# Pink Elephant - Android Project

This project is the source repo for the Pink Elephant social media platform such to contain Android OS. 
The project aims to bring native interface and solution targeted Android API 19.

### How to set up JDK

- You need java 7 version in order to develop Android Applications.

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

- If the Windows is 64-bit then download and install both 32-bit and 64-bit versions. For 32-bit Windows only 32-bit version is enough. For linux/mac get whatever your operation system is.

### How to set up Android SDK

- First of all install Android Studio from given link below. (1.1 GB)

https://developer.android.com/sdk/index.html

- Setup is pretty straight forward. Might have to download more updates. (Takes 6 minutes in SSD)

https://imgur.com/a/1qiDJ

- After setup is finished start Android Studio, first start may take a while. It will start download updates, process is automatic.

![alt tag](http://i67.tinypic.com/30kdrnl.png)

- At startup screen choose "Configurate" then choose "SDK Manager" then press "Launch Standalone SDK Manager".

https://imgur.com/a/ZQHpz

- Press "deselect all" then Select "Updates". Then make sure "Android SDK Platform Tools" are also selected. By default Android Studio always come with latest API version installed. However our application targets api 19 as such we need to install it and other tools.

- You can use the images as I posted below or follow the instructions. (IMAGES ARE ONLY WITH PC'S THAT SUPPORT VIRTULIZATION. THE OPTION MUST BE ENABLED FROM BIOS. GUIDE IS PROVIDED HERE http://www.howtogeek.com/213795/how-to-enable-intel-vt-x-in-your-computers-bios-or-uefi-firmware)

https://imgur.com/a/bicIj

- Go to "Android 4.4.2 (API 19)" Folder and select SDK Platform and Sources. If virtualization is enabled in BIOS choose Intel x86 Atom System Image and Google APIs x86 however if it is not enabled then choose ARM EABI v7a System Image and Google APIs (ARM System Image).

- Go to Extras and select Android Support Library (Optional), Google USB Driver. If your pc supports virtualization also install Intel x86 Emulator Accelerator.

-Press install, accept all of the licenses and press install again. After it is done close the SDK Manager and Android Studio.

### Adding GitHub repo into Android Studio

- If you are using command line to use git, you should go to "C:\Users\<User Name>\AndroidStudioProjects", run cmd/shell there and enter this command "git clone https://github.com/swe574-2015fall-group2/android_repo.git tmp && mv tmp/.git . && rm -rf tmp && git reset --hard".

-For GitHub Client, open the gui, press the + icon, press clone tab, select swe574-2015fall-group2, select android_repo. When Browse for folder appears, go to "C:\Users\<User Name>\AndroidStudioProjects" and press OK.


 






