#version 330 core
#include "phong.glh"

struct Material {
    sampler2D skin;
    bool enableAO;
    sampler2D ambientOcclusion;
    sampler2D specularColor;
    sampler2D specularIntensity;
    float shininess;
//  sampler2D emission;
//  float emissiveness;
};

in vec2 textureCoordinate;
in vec3 normal;
in vec3 fragPos;

uniform Light lights[30];
uniform int lightsCount;
uniform vec3 ambientLight;
uniform vec3 viewPosition;
uniform Material material;

out vec4 FragColor;

void main() {
/*
    vec3 skinColor = texture(material.skin, textureCoordinate).rgb;
    vec3 aoAttenuation;
    if (material.enableAO) {
        aoAttenuation = texture(material.ambientOcclusion, textureCoordinate).rgb;
    } else {
        aoAttenuation = vec3(1);
    }
    vec3 spColor = texture(material.specularColor, textureCoordinate).rgb;
    vec3 spIntensity = texture(material.specularIntensity, textureCoordinate).rgb;
    //    OPPURE CON LENGTH LO USI FLOAT

    vec3 diffuseLights = vec3(0);
    vec3 specularLights = vec3(0);

    for (int i = 0; i < lightsCount; i++) {
        Phong phong = getPhong(lights[i], fragPos, normal, viewPosition, material.shininess);
        diffuseLights += phong.diffuse;
        specularLights += phong.specular;
    }
    vec3 result;
    result += ambientLight * skinColor;
    result += diffuseLights * skinColor;
    result += specularLights * spColor * spIntensity;
    FragColor = vec4(result * aoAttenuation, 1);
    */
    FragColor = vec4(1);
}
