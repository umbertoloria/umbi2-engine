#version 330 core

in vec3 in_position;

uniform mat4 projectionViewTransformation;

void main() {
    gl_Position = projectionViewTransformation * vec4(in_position, 1);
}
