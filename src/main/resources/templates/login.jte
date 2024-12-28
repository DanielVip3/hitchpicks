@param String error
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

        <title>Hitchpicks</title>

        <link rel="stylesheet" href="/generated/css/global.css">
    </head>
    <body class="flex flex-col min-h-screen">
        @template.components.navbar(logged = false, admin = false)

        <div class="flex-grow">
            <div class="md:container md:mx-auto flex items-center justify-center">
                <div class="w-[400px] flex flex-col bg-white border border-t-4 border-t-primary-600 shadow-sm rounded-xl mt-16 shadow-xl shadow-black/25">
                    <div class="p-4 md:p-5 mt-3">
                        <h3 class="text-2xl font-bold text-gray-800 text-left mb-8">Log-in</h3>

                        <form action="/login" method="POST" class="max-w-lg mx-auto space-y-6 flex flex-col items-center">
                            <div class="mx-auto space-y-4 w-full">
                                <div class="space-y-2">
                                    <label for="input-label" class="block text-sm font-medium">Username</label>
                                    <div class="relative">
                                        <input type="text"
                                               id="input-label"
                                               name="username"
                                               class="peer py-4 px-4 ps-11 block w-full bg-gray-100 border-gray-600 rounded-lg text-sm focus:border-primary-600 focus:ring-primary-600 disabled:opacity-50 disabled:pointer-events-none required"
                                               placeholder="Your username..."
                                               minlength="3"
                                               maxlength="25"
                                               required>

                                        <div class="absolute inset-y-0 start-0 flex items-center pointer-events-none ps-4 peer-disabled:opacity-50 peer-disabled:pointer-events-none">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-5">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z"/>
                                            </svg>
                                        </div>
                                    </div>
                                </div>

                                <div class="space-y-2">
                                    <label for="password" class="block text-sm font-medium">Password</label>
                                    <div class="relative">
                                        <input type="password"
                                               id="password"
                                               name="password"
                                               class="peer py-4 px-4 pr-10 ps-11 block w-full bg-gray-100 border-gray-600 rounded-lg text-sm focus:border-primary-600 focus:ring-primary-600 disabled:opacity-50 disabled:pointer-events-none overflow-hidden text-ellipsis"
                                               placeholder="Your password..."
                                               minlength="8"
                                               maxlength="255"
                                               required>

                                        <div class="absolute inset-y-0 start-0 flex items-center pointer-events-none ps-4">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 5.25a3 3 0 0 1 3 3m3 0a6 6 0 0 1-7.029 5.912c-.563-.097-1.159.026-1.563.43L10.5 17.25H8.25v2.25H6v2.25H2.25v-2.818c0-.597.237-1.17.659-1.591l6.499-6.499c.404-.404.527-1 .43-1.563A6 6 0 1 1 21.75 8.25Z"/>
                                            </svg>
                                        </div>

                                        <div class="absolute inset-y-0 end-0 flex items-center pr-4 pl-4 ml-4">
                                            <svg id="togglePassword" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 cursor-pointer">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z"/>
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
                                            </svg>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <button type="submit" class="ml-auto cursor-pointer py-2 px-6 text-sm h-full inline-flex items-center gap-x-2 font-normal rounded-lg border border-transparent bg-primary-600 text-white hover:bg-primary-800 focus:outline-none focus:bg-primary-800 disabled:opacity-50 disabled:pointer-events-none shadow-md shadow-black/25">
                                Log-in
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-4">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 10.5V6.75a4.5 4.5 0 1 1 9 0v3.75M3.75 21.75h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H3.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z"/>
                                </svg>
                            </button>
                        </form>
                        @if (error != null)
                            <div>${error}</div>
                        @endif
                        <p class="mt-8 text-slate-800 text-xs opacity-70">Not registered? <a class="text-blue-600 hover:text-blue-500 decoration-2 hover:underline focus:outline-none focus:underline 0 text-xs text-centered opacity-90" href="#">Create an account</a></p>
                    </div>
                </div>
            </div>
        </div>

        @template.components.footer()

        <script src="/generated/js/preline.js"></script>
        <script src="/js/password-toggle.js"></script>
    </body>
</html>