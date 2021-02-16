**Edit a file, create a new file, and clone from Bitbucket in under 2 minutes**

When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).

------

## Release .apk sign certificate details
file name : Elshare_Keystore.jks
Key Alias : Elshare_Keystore_Alias
Key Store password : Elshare_Keystore

>>> Signer
X.509, CN=Satish W, OU=Devlopment, O=HIVES ONLINE INDIA PVT. LTD, L=Pune, ST=Maharashtra, C=91
[certificate is valid from 9/19/20 3:50 PM to 9/13/45 3:50 PM]
- Signed by "CN=Satish W, OU=Devlopment, O=HIVES ONLINE INDIA PVT. LTD, L=Pune, ST=Maharashtra, C=91"
Digest algorithm: SHA1
Signature algorithm: SHA1withRSA, 2048-bit key

------------------------------------------------------------

## Add sonarqube to localy
How to setup sonarqube localy follow this link :
https://stackoverflow.com/questions/43811106/how-to-integrate-sonarqube-in-android-studio

To download sonarqube setup use following link :
https://www.sonarqube.org/downloads/

To learn about sonarqube basic use following link :
https://www.youtube.com/watch?v=31igoWxauEQ

1. once the download the and unzip sonarqube package go to directory where windows-x86-64 folder present and copy the path
  in my case :
  C:\Users\swarkad\Desktop\HIVES\sonarqube_setup\sonarqube-7.1\sonarqube-7.1\bin\windows-x86-64
2. open command promt and using cd command go to directory
  cd C:\Users\swarkad\Desktop\HIVES\sonarqube_setup\sonarqube-7.1\sonarqube-7.1\bin\windows-x86-64
3. fire command : StartSonar.bat
4. now go to browser and hit address : http://localhost:9000/
   log in using username/password : admin/admin
5. now open our project in Android Studio and go to terminal and fire command : gradlew sonqrqube
6. once the that is done go to browser http://localhost:9000/ and check sonarqube issue and fix it.

--------------------------------------------------------

# LOG HTTP REQUEST & RESPONSE WITH LOGGING INTERCEPTOR :
https://www.youtube.com/watch?v=R2c5Pv5cXc0&t=167s

------------------------------------------------------------

# if you get a list of:
"Duplicate class org.intellij.lang.annotations.... " errors when run the project after adding the implementation to the gradle file.
then solve that using following link :
https://github.com/Inconnu08/android-ratingreviews/issues/12

--------------------------------------------------------------
# if you get issue related 1 or 2  :
 1. Conversion to Dalvik format failed: Unable to execute dex: method ID not in [0, 0xffff]: 65536
 2. trouble writing output: Too many field references: 131000; max is 65536. You may try using --multi-dex option.
  then following the link : https://developer.android.com/studio/build/multidex
 Here to solve multi-dex issue we simply change minSdkVersion to 21 (Android 5.0 : API level 21)

------------------------------------------------------------------

# Network Inspection using Chucker :
1. https://www.youtube.com/watch?v=XUJdaA1We4w
2. https://github.com/ChuckerTeam/chucker

------------------------------------------------------------------