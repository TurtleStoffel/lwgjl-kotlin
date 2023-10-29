#version 330
layout(location=0) in vec3 pos;
layout(location=1) in vec3 normal;
layout(location=2) in vec3 color;

uniform mat4 modelMatrix;
uniform mat4 viewProjectionMatrix;

out vec3 vertexColor;

const vec3 lightDirection = normalize(vec3(1.0f, -0.5f, 1.0f));
const float ambientLighting = 0.35;

void main() {
    vec4 worldNormal = modelMatrix * vec4(normal, 0);
    gl_Position = viewProjectionMatrix * modelMatrix * vec4(pos, 1);
    float brightness = max(dot(lightDirection.xyz, worldNormal.xyz), ambientLighting);
    vertexColor = color * brightness;
}
