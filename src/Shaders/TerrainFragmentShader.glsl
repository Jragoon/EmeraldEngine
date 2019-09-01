#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper; /* Visibility range of specular light */
uniform float reflectivity;

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(toLightVector);

    float brightness = dot(unitNormal, unitLight);
    brightness = max(brightness, 0.1);

    vec3 diffusedLight = brightness * lightColor;

    vec3 unitCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLight;
    vec3 reflectedLight = reflect(lightDirection, unitNormal);
    float specularLightAmount = dot(reflectedLight, unitCamera);
    specularLightAmount = max(specularLightAmount, 0.0);
    float dampedFactor = pow(specularLightAmount, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    out_color = vec4(diffusedLight, 1.0) * texture(textureSampler, pass_textureCoords) + vec4(finalSpecular, 1);
}