#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[7];
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[7];

void main(void) {
    vec4 worldPosition = transformationMatrix * vec4(position.xyz, 1.0);
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position.xyz, 1.0);
    pass_textureCoords = textureCoords;

    surfaceNormal = (transformationMatrix * vec4(normal.xyz, 0.0)).xyz;
    for (int i = 0; i < 7; i++) {
        toLightVector[i] = lightPosition[i] - worldPosition.xyz;
    }
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
}
