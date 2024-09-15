# Compiladores-urbe

<h1>Que es esto?</h1>
Tenemos que desarrollar un compilador, usaremos:
<br>
- Java Maven: que es = https://www.youtube.com/watch?v=rE7zLuQv2IU
<br>
- IDE: Eclipse
<br>
- Flatlaf: Documentacion https://www.formdev.com/flatlaf/
<h1>Uso de git y manejo de versiones</h1>
Aqui les dejo un pequeño manual o guia basica de como sera el trabajo
<br>
Les adelanto que en la rama Main solo va a estar el codigo estable, de alli se creara una nueva rama llamada develop y de ahi las ramas de cada uno de los integrantes
<br>
Ejemplo: Main -> Develop -> FranciscoB/nombre-de-la-feature
<br>
Aqui la guia:
<br>
<h2>1. Configuración Inicial</h2>
<br>
Repositorio Central: Crear un repositorio en GitHub que será el repositorio central donde todos los miembros del equipo colaborarán.
<br>
Clonación: Cada miembro del equipo clona el repositorio central a su máquina local.
<br>
<h2>2. Creación de Ramas</h2>
<br>
Rama Principal (main): Esta es la rama estable donde se encuentra el código listo para producción.
<br>
Ramas de Funcionalidad (feature branches): Cada nueva funcionalidad o tarea se desarrolla en una rama separada. Por ejemplo, si estás trabajando en una nueva característica, puedes crear una rama llamada feature/nueva-caracteristica.
<br>
git checkout -b feature/nueva-caracteristica
<br>
<h2>3. Desarrollo y Actualización</h2>
<br>
Desarrollo en Ramas de Funcionalidad: Los desarrolladores trabajan en sus respectivas ramas de funcionalidad.
<br>
Actualización desde main: Regularmente, los desarrolladores deben actualizar su rama de funcionalidad con los últimos cambios de main para evitar conflictos.
<br>
git fetch origin
<br>
git checkout main
<br>
git pull origin main
<br>
git checkout feature/nueva-caracteristica
<br>
git merge main
<br>
<h2>4. Commit y Push</h2>
<br>
Commits Frecuentes: Hacer commits frecuentes y descriptivos en la rama de funcionalidad.
<br>
git add .
<br>
git commit -m "Descripción del cambio"
<br>
git push origin feature/nueva-caracteristica
<br>

<h2>5. Pull Request</h2>
Crear Pull Request: Una vez que la funcionalidad está completa, se crea un pull request (PR) desde la rama de funcionalidad hacia main en GitHub.
<br>
Revisión de Código: Otros miembros del equipo revisan el PR, sugieren cambios y aprueban el código.
<br>
<h2>6. Integración y Despliegue</h2>
Merge: Después de la aprobación, el PR se fusiona en main.
<br>
Despliegue: El código en main se despliega a producción si es necesario.
<br>
<h2>7. Resolución de Conflictos</h2>
Conflictos de Merge: Si hay conflictos durante el merge, se resuelven manualmente y se actualiza el PR.
