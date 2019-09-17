#version 330 core

in vec3 in_position;
in vec3 in_normal;

uniform mat4 projectionView;
uniform mat4 transformation;

out vec3 normal;
out vec3 fragPos;
out vec3 viewPosition;

void main() {
    // To prevent non-linear scaling
    normal = transpose(inverse(mat3(transformation))) * in_normal;

    vec4 worldPosition = transformation * vec4(in_position, 1);
    fragPos = vec3(worldPosition);
    gl_Position = projectionView * worldPosition;
    // viewPosition = vec3(inverse(view) * vec4(0, 0, 0, 1));
}
