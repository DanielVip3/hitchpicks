<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <title>Hitchpicks</title>

    <link rel="stylesheet" href="/generated/css/global.css">
</head>
<body class="flex flex-col min-h-screen">
@template.components.navbar()

<div class="flex-grow">
    <!-- Full-width fluid until the `md` breakpoint, then lock to container -->
    <div class="md:container md:mx-auto flex items-center justify-center">
        <!-- ... -->
        <div class="w-[400px] flex flex-col bg-white border border-t-4  border-t-primary-600  shadow-sm rounded-xl">
           <div class="p-5 md:p-5 mt-3">
                <h3 class="text-2xl font-bold text-gray-800 text-center mb-8">
                    Login
                </h3>
                <form class="max-w-sm mx-auto space-y-6 flex flex-col items-center">
                    <div class="max-w-sm space-y-3">
                        <div class="relative">
                            <input type="email"
                                   class="peer py-4 px-4 ps-11 block w-full bg-gray-100 border-transparent rounded-lg text-sm focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none"
                                   placeholder="Enter email">
                            <div class="absolute inset-y-0 start-0 flex items-center pointer-events-none ps-4 peer-disabled:opacity-50 peer-disabled:pointer-events-none">
                                <svg class="shrink-0 size-4 text-gray-500"
                                     xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round">
                                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                                    <circle cx="12" cy="7" r="4"></circle>
                                </svg>
                            </div>
                        </div>

                        <div class="relative">
                            <input type="password"
                                   class="peer py-4 px-4 ps-11 block w-full bg-gray-100 border-transparent rounded-lg text-sm focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none"
                                   placeholder="Enter password">
                            <div class="absolute inset-y-0 start-0 flex items-center pointer-events-none ps-4 peer-disabled:opacity-50 peer-disabled:pointer-events-none">
                                <svg class="shrink-0 size-4 text-gray-500"
                                     xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round">
                                    <path d="M2 18v3c0 .6.4 1 1 1h4v-3h3v-3h2l1.4-1.4a6.5 6.5 0 1 0-4-4Z"></path>
                                    <circle cx="16.5" cy="7.5" r=".5"></circle>
                                </svg>
                            </div>
                        </div>
                    </div>

                    <button type="submit"
                            class="py-3 px-4 inline-flex items-center gap-x-2 text-sm font-medium rounded-lg border border-transparent bg-primary-600 text-white hover:bg-blue-700 focus:outline-none focus:bg-blue-700 focus:outline-none focus:bg-blue-700 disabled:opacity-50 disabled:pointer-events-none">
                        Log In
                    </button>
                </form>

               <p><a class="text-blue-600 hover:text-blue-500 decoration-2 hover:underline focus:outline-none focus:underline opacity-90 text-xs text-centered" href="#">Not registered? Create an account</a></p>

           </div>
        </div>
    </div>
</div>
@template.components.footer()

<script src="/generated/js/preline.js"></script>
</body>
</html>