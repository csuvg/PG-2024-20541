# Quauhtlemallan

El objetivo general de este proyecto es desarrollar una aplicación móvil educativa que fomente el interés por aprender más sobre la geografía y temas culturales de Guatemala, dejando la misma como una base para futuras mejoras o adaptaciones en estos u otros campos de estudio. 

El proyecto se centra en temas de **gamificación** y alinea todos los componentes a la rica diversidad cultural del país. Se utilizaron herramientas y técnicas actuales como **Jetpack Compose**, arquitectura **MVVM** y refinamiento de un modelo de Inteligencia Artificial con datasets recopilados manualmente. Estas características buscan ofrecer una experiencia educativa única e interactiva.

## Tabla de Contenidos

- [Estructura del Proyecto](#estructura-del-proyecto)
- [Características](#características)
- [Instalación](#instalación)
- [Video Demo](#demostración-de-funcionamiento)
- [Evaluación del Proyecto](#evaluación-del-proyecto)
- [Informe Final](#informe-final)

## Estructura del Proyecto

El repositorio cuenta con los siguientes directorios principales:

- **`.github/workflows`**: Contiene un workflow encargado de actualizar la branch `develop` luego de cada cambio en `master` para mantener un flujo adecuado utilizando GitFlow.
- **`Android_App`**: Proyecto de Android Studio que representa la aplicación móvil.
- **`Google_Cloud`**: Archivos utilizados para desplegar un servicio en **Google Cloud Run** que conecta la aplicación con modelos alojados en Hugging Face.
- **`Kukul_Bot`**: Contiene datasets y notebooks necesarios para el fine-tuning de un modelo **Llama 2**. Este modelo se especializa en la generación de texto relacionado con la cultura y geografía de Guatemala.

## Características

- **Interfaz amigable e intuitiva**: Desarrollada con Jetpack Compose y Kotlin.
- **Modelo de IA especializado**: Refinado con datos culturales y geográficos de Guatemala.
- **Sistema gamificado**: Incluye retos y niveles relacionados con la diversidad cultural del país.

## Instalación

Para poder ejecutar todo el proyecto es necesario preparar 3 ambientes:
- Ambiente Android
- Endpoints Modelo de generación de texto
- Servicio para conectar aplicación y endpoints

El primer paso sería clonar este repositorio:

```bash
  git clone <url del repositorio>
```

Posterior a eso se trabajan los diferentes ambientes:

### Android

Con ayuda de Android Studio se carga el proyecto, la carpeta [Android App](/Quauhtlemallan/src/Android_App/), (y con un dispositivo virtual o físico instalado) se ejecuta la aplicación y se deberían instalar todas las dependencias necesarias. De aparecer un error como la imagen que se muestra a continuación:

![image](https://github.com/user-attachments/assets/326f51b8-90c2-4c26-a3ca-0d0ab2f3bd0a)

Es necesario iniciar Android Studio con permisos de administrador y una vez se ejecuta de esta forma la aplicación debería iniciarse automáticamente y ya estaría disponible para su uso.

### Endpoints

![image-1](https://github.com/user-attachments/assets/d114a12c-7858-42fd-aac1-c42a0984e34d)

Una vez se inicia sesión en Hugging Face y se tiene acceso a los diferentes datasets y modelos es posible subir y descargar los mismos. En la carpeta [Kukul](/Quauhtlemallan/src/Kukul_Bot/) se encuentran los notebooks y datasets utilizados; Para poder subir modelos de autoría propia se pueden seguir los pasos del [Notebook de Fine Tunning](/Quauhtlemallan/src/Kukul_Bot/Fine_tune_Llama2_Kukul.ipynb). Posterior a tener el modelo ubicado se trabajara en la página de Endpoints https://huggingface.co/inference-endpoints/dedicated, con una cuenta creada se podrán levantar diferentes endpoints desde el dashboard:

![image-2](https://github.com/user-attachments/assets/27c0d112-29c2-402c-bc2b-c0eb2e50b3ec)

En cada endpoint se solicita una serie de datos relacionado al modelo sobre el que se trabajara. Se usará el nombre del modelo y se configurará acorde a las necesidades. Para el caso del proyecto se siguieron los datos recomendados.

![image-3](https://github.com/user-attachments/assets/2b46310a-227a-49a9-b8a2-de16570cd9fd)

Y una vez este es creado se activa o desactiva conforme su uso para evitar costos. Para el caso de Kukul se debe utilizar el modelo en su versión 3: https://huggingface.co/ChrisG19/Llama-2-7b-kukul-bot-v3 que fue la versión final. Luego de que este endpoint está creado se procedería a configurar el servicio cloud.

### Servicio Cloud

Usando el código de la carpeta [Google Cloud](/Quauhtlemallan/src/Google_Cloud/) se creará un contenedor de Cloud Run para levantar un servicio que permita la transferencia de datos.

![image-4](https://github.com/user-attachments/assets/5421c193-37e5-46ef-b85b-b2e5f40ed756)

Desde la consola de Google Cloud se creará un proyecto y dentro de este se buscará el apartado de **Cloud Run**.

![image-5](https://github.com/user-attachments/assets/a9387647-0e73-41fb-a9cf-3987f556b1f6)

Una vez dentro de este módulo se creará un servicio y accediendo a la consola y accediendo al editor:

![image-6](https://github.com/user-attachments/assets/bb0cecef-9d16-4930-b8e3-eaf759b4bbd3)
![image-7](https://github.com/user-attachments/assets/fa4cb42d-4fcc-45d0-9fe0-6ddd0366cb54)

Se copiaran los archivos en una carpeta (en este caso fue llamada chatbot) y en la parte inferior izquierda se accederá a las opciones cloud

![image-8](https://github.com/user-attachments/assets/444e8c02-19f2-4c16-8e42-3531f3560f20)
![image-9](https://github.com/user-attachments/assets/0c5042ab-798c-4e10-a3bb-41e4295108a0)

Esto solicitará acceso a credenciales y posterior a autorizar se configurará el despliegue.

![image-10](https://github.com/user-attachments/assets/ed36f035-80ec-4303-a228-1930150c6c59)

Para poder hacer pruebas del servicio se correrá con el emulador de Cloud Run y posterior a revisar que todo funciona bien (se levantará el contenedor y Google ofrecerá una url para poder revisar el estado del servicio). Una vez se evalua que todo funciona se procede a desplegar el servicio y esto consta de seguir los pasos que Google menciona.

![image-11](https://github.com/user-attachments/assets/92a722d9-119f-4ba7-b89d-fd3def25d9b8)

Al finalizar todo este proceso el servicio quedará arriba y a través de la url que ofrece el servicio se podrá consultar el mismo para realizar las transacciones entre la aplicación y el endpoint de Hugging Face.

## Demostración de funcionamiento
  
- [Funcionamiento de aplicación final](/Quauhtlemallan/demo/FuncionamientoFinal.mp4)
- [Funcionamiento de endpoint](/Quauhtlemallan/demo/KukulEndPoint.mp4)

## Evaluación del Proyecto

Se evaluaron grupos de diferentes edades para determinar:
- La facilidad de uso de la aplicación.
- Su aporte en la reducción de brechas de conocimiento.
- La efectividad de la gamificación como herramienta educativa.
- La efectividad del fine-tunning realizado para especializar un modelo de generación de texto.

Los resultados indicaron que las aplicaciones gamificadas aportan significativamente al aprendizaje y pueden ser utilizadas como método de enseñanza en temas culturales y geográficos.

## Informe Final

- [Informe Final](/Quauhtlemallan/docs/Proyecto_Quauhtlemallan.pdf)
