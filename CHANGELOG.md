# v8.2.0
## Agregado
- Se vuelve a agregar metodo TypefaceHelper.getTypeface(context, font, callback) por compatabilidad. Se depreca su uso por TypefaceHelper.getFontTypeface(context, font)

# v8.1.0
## Agregado
- Se agregan algunas annotations @Nulleable dado que la typeface puede resultar null en el caso de Calligraphy. Una vez que eliminemos la lib podemos depender de una typeface no nulleable.
- Se agrega TypefaceSpanCompat para remplazar CalligraphyTypefaceSpan

# v8.0.0
## Cambiado
- TypefaceHelper.getTypeface se depreca por TypefaceHelper.getFontTypeface
- Se cambia firma de TypefaceHelper.getTypeface para que retorne un Typeface en vez de responde mediante un ResourcesCompat.FontCallback

# v7.1.0
## Agregado
- Se agrega soporte al caso de `Small` para MeliButton, segun Andes.

# v7.0.0
## Arreglado
- Se vuelve a agregar el attr autostart en MeliSpinner por retro compatibilidad

## Cambiado
- Se actualiza la lib de fresco 1.13.0.

# v6.1.0
## Cambiado
- MeliSpinner autostart attr deprecado.
- MeliSpinner.start() y MeliSpinner.stop deprecado.
- MeliSpinner controla ciclo de animacion basandose en onAttachedToWindow / onDetachedFromWindow
- LoadingSpinner ahora solo agrega listener de animations cuando se va ejecutar la animacion y no en su constructor para prevenir leaks de memoria.

# v6.0.1
## Cambiado
- Downgraded Bintray Plugin version.
- Downgraded Gradle version.

# v6.0.0
## Cambiado
- Se sube el min API level al que usamos (16)
- Actualización de dependencias
- Actualización de Gradle 4.10.3
- Se cambia el package de `com.mercadolibre.android.ui.widgets.animationManager` a `com.mercadolibre.android.ui.widgets.animationmanager` por un lint de PMD
- Migración a API 28 y Support Library 28.0.0

# v5.11.0
## Agregado
- Se agrega dialogo que se muestra en pantalla completa y con nuevo estilo

# v5.10.1
## Eliminado
- Cosas de los build.gradle que están de más.
## Arreglado
- Se corrige el comportamiento del teclado cuando el componente `TextField` toma el foco.
- Se corrige la reconstrución de la instancia del componente `TextField` cuando ocurre un cambio de configuración.

# v5.10.0
## Nuevo
- Se agrega el color ui_components_action_bar_text_color a la paleta.

# v5.9.0
## Nuevo
- Se marca como deprecado el Theme.MLTheme, en pos del nuevo Theme.Base que favorece la agregacion haciendo que cada negocio lo implemente con sus propios items

# v5.8.0
## Cambiado
- Actualizado bintray user

# v5.7.2
## Agregado
- Se agrega metodo a TypefaceHelper para obtener un typeface. `#getTypeface(context, font, fontCallback)`. El mismo se recibe desde el callback ya que dependiendo de la imple puede ser sincronico o asincronico el fetch

# v5.7.0
## Nuevo
- Se agrega un theme `Theme.Base` que tiene **temporalmente** como parent a `Theme.MLTheme`. Este theme se encuentra vacio para ser overwriteado por los configurators de apps correspondientes.
- Se agrega una interfaz `TypefaceHelper.TypefaceSetter` que permite customizar el comportamiento de como el `TypefaceHelper` setea las fuentes, esto nos permite descoplarnos de `Calligraphy` para el seteo de las mismas y utilizar otros features availables como Downloadable Fonts o alguna imple propia.

# v5.6.2
## Arreglado
- Para android 23+ se muestra el status bar light (con icono oscuros).

# v5.6.1
## Arreglado
- No publicó 5.6.0

# v5.6.0 
### Arreglado
- Fix memory leak en el MeliSpinner

# v5.5.1
### Arreglado
- No incluye nada, es una publicación para limpiar el delta de commits de develop para que no parezca que hay que relseasearla.

# v5.5.0
### Nuevo
- Se agregaron otros 3 colores para poder customizar el theme desde las apps.

# v5.4.1
- MLTheme customizations added

# v5.4.0
- MLTheme ahora permite ser customizado

# v5.3.0
- MLTheme deja de extender de MLThemeLegacy y ahora permite ser customizado

# v5.2.1
- Fix font set on MeliButton
- Dimens added

# v5.2.0
- Changes

# v5.1.0
- Turn on CircleCI 
