#version 330 core

in vec3 in_position;
in vec2 in_texCoord;

uniform mat4 projectionView;
uniform mat4 transformation;

out vec2 tco;

void main() {
    tco = in_texCoord;
    gl_Position = projectionView * transformation * vec4(in_position, 1);
}
