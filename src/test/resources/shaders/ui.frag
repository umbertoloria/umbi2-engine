#version 330 core
#include "phong.glh"

uniform vec4 background;

out vec4 FragColor;

void main() {
    FragColor = background;
    if (FragColor.a <= 0.5)
        discard;
}
