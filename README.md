# Pink Elephant - Android Project

This project is the mobile source repo for the Pink Elephant social media platform such to contain Android OS. 
The project aims to bring native interface and solution targeted Android API 19.

Pink Elephant is a project dedicated to gather communities in a productive way so that they can spend more time to socialize and improve new ideas, rather than struggling with collecting and organizing it.  

Our project is an outcome of SWE 574 - Software Development as a Team course in Computer Engineering Department, Boğaziçi University.

Please also examine [the Project Wiki page](https://github.com/swe574-2015fall-group2/core_repo/wiki/Pink-Elephant-Wiki) including all documentation such as SPM, RSD, DSD and more.  

Background information of our project is provided below. You can also follow mobile development instructions below.

###Background Information Of The Project 

Communities of Practice / Communicating the Globe
 
Most of the communities are unable to coordinate in an effective way, or many people with common interests are unable to create a community.
 
There is also a need for academic ad-hoc groups to practice on a subject. When they want to talk about an issue or a paper, there are two types of communication are being formed:
 
- Synchronous - "Let's discuss it!"
- Asynchronous - "Let's meet! Where, when?"
 
As a result, some knowledge is generated. That needs to be recorded in a useful way. One should also find past meetings easily.
 
There would be new members, Some other may leave the community. All members also may be administered.
 
**Standard problem** - How to plan and coordinate interactions and document them in a usable form?
 
Dropbox, Doodle, E-mail - They are inefficient and they are not holistic. A meeting has to be coordinated in a particular manner.
 
**AGENDA**

- Meeting between 8 pm - 9 pm / Istanbul time
- Who will come?
- Who will manage the meeting?
- How it will be documented? Who will document it?
- Outcome of the meeting?
- Classification of meetings? (Semantic tagging)  

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

- Press install, accept all of the licenses and press install again. After it is done close the SDK Manager and Android Studio.

### Adding GitHub repo into Android Studio

- If you are using command line to use git, you should go to "C:\Users\<User Name>\AndroidStudioProjects", run cmd/shell there and enter this command for linux "git clone https://github.com/swe574-2015fall-group2/android_repo.git tmp && mv tmp/.git . && rm -rf tmp && git reset --hard" or this command for windows "git clone https://github.com/swe574-2015fall-group2/android_repo.git tmp && move tmp/.git . && del -rf tmp && git reset --hard".

- For GitHub Client, open the gui, press the + icon, press clone tab, select swe574-2015fall-group2, select android_repo. When Browse for folder appears, go to "C:\Users\<User Name>\AndroidStudioProjects" and press OK.

### Setting up platform-tools

- First of all locate where the platform tools are installed. By default they are located at "C:\Users\<User Name>\AppData\Local\Android\sdk\platform-tools".

- To debug applications platform tools must be added to the path. To do this right click on "My Computer" then "Advanced System Settings". From new window press "Advanced" then "Enviroment Variables". At "System Variables" menu, go down until you found PATH variable. Press Edit button, From the new menu, go to end of the item Variable Value. Put a ";" of the end of it, if it doesn't exist then put the path of android platform tools as such given below. (Note that paths doesn't need to be same)

https://imgur.com/a/gf7G0

- Press OK to the all menus until we are back to desktop. Close any instance of cmd, if it is open.

- To check if it is correctly installed, open new instance of cmd then enter this command "adb devices". If it says "List of Attechted Devices" then it is installed correctly.

### Setting up debug devices

- There are two ways to debug Android Applications. First is using actual device (reccomended).
- Open your device and go to "Settings" app. From there go to "About Phone" list item. Find the item called "Build Number". Now press on it 5 times, the phone will say "You become a developer now".
- Go back to settings app and press developer options, Press ON if it is required then make sure "USB debugging" is on.
- Connect your phone to your pc. Now enter "adb kill-server" then "adb devices" into cmd. Make sure your phone's screen is on.
- The phone will be promt authentication, press okay and make sure you check the box says always trust this pc.
- Press adb devices again, now it shuold show your device with a serial number.

- Second way is using AVD (Android Virtual Device). To create AVD open Android Studio.
- Our Git-Hub project should have appered at the left side of the menu screen now, below of the recent projects. Select that. If not press open an exisiting Android Studio project then navigate to the repo folder. First run might take a while due to gradle integration.
- Press AVD Manager icon from right side of the run buttons. (Looks like tablet with android).
- Remove the default API 23 device.Then press Create Virtual Device.
- Select Nexus 4 from the list, then select kitkat with Google Inc.Press next then finish.





 






