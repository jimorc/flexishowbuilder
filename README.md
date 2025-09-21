# flexishowbuilder
flexishowbuilder is a program that takes a CSV file containing image file and owner names 
downloaded from my photography club website, and builds title images and an Excel spreadsheet 
(XLS) file for input into flexishow to display the slideshow. These slideshows are used to 
display images for competitions, non-competition events which we call Theme Nights, and in 
special interest groups.

## License
The license is provided in a file called 
LICENSE. At the moment, this is the BSD 
3-Clause License, but it may be necessary to 
modify this to a more restrictive license depending on the licensing of the various libraries
that this project uses.

## Inputs
### CSV File
To be provided.

### Images
As required by flexishow, all images must be in JPEG format.

## Building flexishowbuilder
I use Visual Studio Code so the instructions below are for that IDE. You may also be able to
get it to build from the command line. Where possible, I will provide insttructions for that
as well.

### Linux
The following instructions work for Linux Mint 22.2. You should be able to modify them
for your Linux distro:

#### Download flexishowbuilder Project
Open a terminal and enter:
```bash
cd <your java folder>
git clone https://github.com/jimorc/flexishowbuilder.git
```
and follow the instructions for VSCode or the command line.

#### Install Visual Studio Code
Install VSCode from the snap store:
```bash
sudo snap install code
```
Start VSCode and install these extensions:
* Extension Pack for Java - from Microsoft
You might also want:
* Print - from PD Consulting

#### Install Java
I use openjdk from the snap store. This is newer than the version in the Linux Mint (actually
Ubuntu) repository.
To install openjdk from the snap store, enter the following in a terminal:
```bash
sudo snap install openjdk
```

#### Download JavaFX
Follow the JavaFX Getting Started instructions.

#### Building flexishowbuilder Using VSCode
To build flexishowbuilder from source, perform the following steps:
```bash
cd <your java folder>/flexishowbuilder
code .
```
In VSCode press Ctrl-F5.

#### Building flexishowbuilder at the Command Line

__The following should work, but currently throws an UnsupportedOperationException.__

To build flexishowbuilder at the command line, open a terminal and enter:
```bash
cd <your java folder>/flexishow/src/flexishow
java --module-path $PATH_TO_FX --add-modules javafx.controls --enable-native-access=javafx.graphics BuilderGUI.java
```

### Windows
To be added

### MacOS
To be added