<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

        <title>Hitchpicks</title>

        <link rel="stylesheet" href="/generated/css/global.css">
    </head>
    <body class="flex flex-col min-h-screen">
        @template.components.navbar(logged = true, admin = false)

        <div class="flex-grow">
            <div class="mt-2 bg-gray-800 text-sm text-white rounded-lg p-4" role="alert" tabindex="-1" aria-labelledby="hs-solid-color-dark-label">
                <span id="hs-solid-color-dark-label" class="font-bold">Testing</span> alert! This is to test Tailwind and Preline UI.
            </div>
        </div>

        @template.components.footer()

        <script src="/generated/js/preline.js"></script>
    </body>
</html>