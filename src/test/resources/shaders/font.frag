#version 330 core

in vec2 tco;

uniform sampler2D textureChannel;
uniform vec2 textureCoordinate;
uniform vec2 unitSize;

out vec4 out_color;

// TODO: Usare BLEND
void main() {
    out_color = texture(textureChannel, (tco + textureCoordinate) * unitSize);
    out_color = vec4(vec3(1, 1, 1) - out_color.rgb, out_color.a);
    if (out_color.rgb == vec3(1))
        discard;
}
