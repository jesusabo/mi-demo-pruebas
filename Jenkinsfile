pipeline {
    agent any
    parameters {
        string(name: 'DEPLOY_ENV', defaultValue: 'dev', description: 'Ambiente de despliegue')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Ejecutar pruebas')
                 }
    stages {
        stage('Build'){
            steps{
                echo "BUILD no disponible"
            }
        }
        stage('Tests'){
            when {
                expression { params.RUN_TESTS == true }
            }
            steps{
                echo "Ejecutando pruebas en el entorno: ${params.DEPLOY_ENV}"
            }
        }
        stage('Deploy'){
            steps{
                echo "Desplegando en el ambiente: ${params.DEPLOY_ENV}"
                echo "DEPLOY OK!!!"
            }
        }
    }
}