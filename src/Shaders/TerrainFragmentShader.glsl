#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[7];
in vec3 toCameraVector;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor[7];
uniform vec3 attenuation[7];
uniform float shineDamper; /* Visibility range of specular light */
uniform float reflectivity;

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i = 0; i < 7; i++) {
        vec3 unitLight = normalize(toLightVector[i]);

        float distance = length(toLightVector[i]);
        float attenuationFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);

        float brightness = dot(unitNormal, unitLight);
        brightness = max(brightness, 0.0);

        vec3 lightDirection = -unitLight;
        vec3 reflectedLight = reflect(lightDirection, unitNormal);

        float specularLightAmount = dot(reflectedLight, unitCamera);
        specularLightAmount = max(specularLightAmount, 0.0);
        float dampedFactor = pow(specularLightAmount, shineDamper);
        totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attenuationFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attenuationFactor;
    }
    totalDiffuse = max(totalDiffuse, 0.2);

    out_color = vec4(totalDiffuse, 1.0) * texture(textureSampler, pass_textureCoords) + vec4(totalSpecular, 1);
}