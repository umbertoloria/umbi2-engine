#version 330 core
#include "phong.glh"

struct Material {
    vec4 diffuse;
    vec4 specular;
    float shininess;
};

in vec3 normal;
in vec3 fragPos;

uniform PointLight pointLights[30];
uniform int pointLightsCount;
uniform DirectionalLight directionalLights[30];
uniform int directionalLightsCount;
uniform SpotLight spotLights[30];
uniform int spotLightsCount;
uniform int lightEffect;
uniform vec3 viewPosition;
uniform Material material;

out vec4 FragColor;

void main() {
    vec3 norm = normalize(normal);
    vec3 viewDir = normalize(viewPosition - fragPos);
    vec4 ambient = vec4(0.0);
    vec4 diffuse = vec4(0.0);
    vec4 specular = vec4(0.0);
    for (int i = 0; i < directionalLightsCount; i++) {
        Phong x = calcDirectionalLight(directionalLights[i], norm, viewDir, material.shininess, lightEffect);
        ambient += vec4(x.ambient, 1.0);
        diffuse += vec4(x.diffuse, 1.0);
        specular += vec4(x.specular, 1.0);
    }
    for (int i = 0; i < pointLightsCount; i++) {
        Phong x = calcPointLight(pointLights[i], norm, fragPos, viewDir, material.shininess, lightEffect);
        ambient += vec4(x.ambient, 1.0);
        diffuse += vec4(x.diffuse, 1.0);
        specular += vec4(x.specular, 1.0);
    }
    for (int i = 0; i < spotLightsCount; i++) {
        Phong x = calcSpotLight(spotLights[i], norm, fragPos, viewDir, material.shininess, lightEffect);
        ambient += vec4(x.ambient, 1.0);
        diffuse += vec4(x.diffuse, 1.0);
        specular += vec4(x.specular, 1.0);
    }
    ambient *= material.diffuse;
    diffuse *= material.diffuse;
    specular *= material.specular;
    FragColor += ambient + diffuse + specular;
}
