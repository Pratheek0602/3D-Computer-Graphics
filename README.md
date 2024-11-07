# 3D-Computer-Graphics

## Overview
This project is a 3D computer graphics simulation developed in OpenGL, featuring an interactive environment inside a virtual spacecraft with robots that move and respond to user inputs. The project showcases advanced rendering techniques, shader programming, and scene graph hierarchies for realistic animations and lighting.

## Features
- **Interactive 3D Robots**: Articulated robots that perform animations and have movable parts.
- **Custom Shader Programming**: Implemented vertex and fragment shaders to achieve realistic lighting effects, reflections, and textures.
- **Dynamic Animations**: Robots are animated to move in response to user commands, showcasing real-time transformations and joint movements.
- **User Interaction**: Control robots with start, stop, and reset functionalities, and toggle lights within the scene.

## Getting Started

### Prerequisites
Ensure you have the following installed:
- **Java Development Kit (JDK)** – to compile and run the project.
- **JOGL (Java Bindings for OpenGL)** – for rendering 3D graphics.

### Installation
Clone this repository to your local machine:

```bash
git clone https://github.com/Pratheek0602/3D-Computer-Graphics.git
cd 3D-Computer-Graphics
```
Verify that the lib and jar directories include the appropriate JOGL libraries (jogl-all.jar and gluegen-rt.jar).

## Running the Project
Set up your PATH and CLASSPATH to include the JOGL libraries and compile the Java files. Replace /path/to with your actual directory path:

```bash
export PATH=/path/to/3D-Computer-Graphics/lib:$PATH
export CLASSPATH=.:/path/to/3D-Computer-Graphics/jar/jogl-all.jar:/path/to/3D-Computer-Graphics/jar/gluegen-rt.jar:$CLASSPATH

javac *.java
java Spacecraft
```
## Demo Video
Watch the demo video showcasing the project in action:

[Download Demo Video](./assets/video/demo.mp4)









