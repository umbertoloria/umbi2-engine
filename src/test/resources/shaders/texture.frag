#version 330 core

in vec2 textureCoordinate;

uniform sampler2D textureChannel;

out vec4 out_color;

void main() {
    out_color = texture(textureChannel, textureCoordinate);
}
