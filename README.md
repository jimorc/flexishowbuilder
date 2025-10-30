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
I use openjdk from the repository.
To install openjdk from the snap store, enter the following in a terminal:
```bash
sudo snap apt install openjdk
sudo snap apt maven
nano ~/.bashrc
```

Test that everything is correct:
```bash
java -version
mvn -version
```

#### Install Other Tools
```bash
sudo apt install rpm
```

#### Download JavaFX
Follow the JavaFX Getting Started instructions.
Add the following to ~/.bashrc
```
export PATH_TO_FX=<location of javafx>/javafx-sdk-25/lib
```

#### Building flexishowbuilder Using VSCode
To build flexishowbuilder from source, perform the following steps:
```bash
cd <your java folder>/flexishowbuilder
code .
```
In VSCode press Ctrl-F5.

#### Building flexishowbuilder at the Command Line

To build flexishowbuilder at the command line, open a terminal and enter:
```bash
cd <your java folder>/flexishowbuilder
mvn clean
mvn checkstyle:check
mvn package
mvn jpackage:jpackage
```
The generated jar file is located at `<your java folder>/flexishowbuilder/target/flexishowbuilder-<version>.jar` where `<version>` is similar to `0.1-alpha`.
The generated deb package is located at `<your java folder>/flexishowbuilder/target/dist/flexishowbuilder-<version>_amd64.deb` where `<version>` is similar to `0.1-alpha`.
The generated rpm file is located at `<your java folder>/flexishowbuilder/target/rpm/flexishowbuilder/RPMS/noarch/flexishowbuilder-<version>.1.njoarch.rpm' where <version>` is similar to `0.1-alpha`.

### Windows
To be added

### macOS
#### Install Building Tools
1. XCode must be installed. It is available on the Mac App Store.

2. Both Java and Maven are required. openjdk (Java) and maven are both available on Homebrew but the Homebrew version of openjdk links to a
shared version of harfbuzz, which is not installed by default on MacOS. Installing maven from
Homebrew will install openjdk from Homebrew if it is not alreay installed.
Using these installations,
a DMG package for flexishowbuilder can be built and installed. Unfortunately, flexishowbuilder
will abort on
systems on which the harfbuzz library has not been installed. Therefore, a different source for
both Java and maven must be found.
An up-to-date version of Java is available from Oracle, and Maven is available using MacPorts. So,
for Maven, [MacPorts](https://www.macports.org/install.php) is required. Installation of Java and
Maven are detailed in the next two steps.

3. Install Java:
In a terminal window, enter:
java --version
```
If the version information specifies openjdk, then you must uninstall openjdk. If not openjdk, but
the version is less than 25, [download Java from
Oracle](https://www.oracle.com/java/technologies/downloads/), and install it.

Open ~/.zshrxc (or ~/.bashrc if you use bash instead of zsh) in an editor and enter:
```
export JAVA_HOME=$(usr/libexec.java_home)
```
and in the terminal:
```zsh
source ~/.zshrc
java --version
```
The version information should now indicate a Java version that is not openjdk, and a version of
25 or greater.

4. Install maven
```zsh
sudo port install maven
mvn --version
```

5. Install the JavaFX SDK. Instructions for installing the JavaFX SDK are available from
[Gluon](https://gluonhq.com/products/javafx). Extract the downloaded zip file
and place it somewhere permanent. A good location would be in your Java projects
folder.

Add the following to ~/.zshrc:
```
export PATH_TO_JAVAFX="/path/to/javafx-sdk-25/lib"
```
Replace `25` with the version that you downloaded.
Again, in the terminal:
```zshrc
source ~/.zshrc

6. Install git
In a terminal, enter:
```
git --version
```
If git is not installed, then install it from either MacPorts, or Homebrew. The following
instructions will install it from MacPorts. In a terminal, enter:
```zsh
brew install git
git --version
```

7. Clone flexishowbuilder.
```zsh
cd <your-projects-directory>
git clone https://github.com/jimorc/flexishowbuilder.git
```

8. Install the IDE of your choice. Not a requirement, but it helps speed development.

#### Build flexishowbuilder
```zsh
cd <your-projects-folder>/flexishowbuilder
mvn clean
mvn package
mvn jpackage/jpackage
```
These steps produce the following artifacts:
- flexishowbuilder/target/flexishowbuilder-<version>.jar
- flexishowbuilder/target/flexishowbuilder-<version>-jar-with-dependencies.jar
- flexishowbuilder/target/dist/flexishowbuilder-<version>.dmg

Install the DMG file as a final test. If you have a Mac without the build tools installed, then
install the DMG on that system and run flexishowbuilder to completion to ensure that the
executable can access all of the libraries it needs.
