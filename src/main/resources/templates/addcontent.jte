<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <title>Hitchpicks</title>
        <link rel="stylesheet" href="/generated/css/global.css">
    </head>
    <body>
        @template.components.navbar()
        <!-- justify between da guardare meglio oltre che aggiungere justify-center item-center -->
        <div class="w-[1442px] h-[790px] p-4 bg-white border-t-4 border-gray-300 border-t-teal-600 rounded-lg shadow-lg justify-between flex flex-col mx-auto">
            <div class="my-4 px-12 w-full">
                <span class="text-lexend text-[32px] font-medium">Add Content</span>
            </div>

            <!-- Secondo div con layout flex orizzontale -->
            <div class="flex flex-row bg-white p-4 rounded-lg px-12 w-full space-x-4">
                <!-- Primo div interno con flex verticale -->
                <div class="flex-1 flex flex-col bg-blue-200 p-4 rounded-lg">
                    <div class="mb-6">
                        <div class="flex justify-between items-center mb-2">
                            <span class="text-gray-500 text-lexend text-[15px] font-medium">IMDB Auto-Import</span>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="gray" class="size-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="m3.75 13.5 10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75Z" />
                            </svg>
                        </div>
                        <div>
                            <label for="hs-trailing-button-add-on-with-icon-and-button" class="sr-only">Label</label>
                            <div class="relative flex rounded-lg shadow-sm">
                                <input type="text" id="hs-trailing-button-add-on-with-icon-and-button"
                                       name="hs-trailing-button-add-on-with-icon-and-button"
                                       class="py-3 px-4 ps-11 block w-full h-11 border-gray-200 shadow-sm rounded-s-lg text-[15px] font-lexend font-medium focus:z-10 focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none"
                                       placeholder="Search IMDB Identifier">
                                <div class="absolute inset-y-0 start-0 flex items-center pointer-events-none z-20 ps-4">
                                    <svg class="shrink-0 size-4 text-gray-400" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <circle cx="11" cy="11" r="8"></circle>
                                        <path d="m21 21-4.3-4.3"></path>
                                    </svg>
                                </div>
                                <button type="button" class="py-3 px-4 inline-flex justify-center items-center gap-x-2 text-sm font-semibold rounded-e-md border border-transparent bg-teal-600 text-white hover:bg-teal-700 focus:outline-none focus:bg-teal-700 disabled:opacity-50 disabled:pointer-events-none">Import</button>
                            </div>
                        </div>
                    </div>

                    <div class="mb-6">
                        <div class="flex justify-between items-center mb-2">
                            <span class="text-gray-500 text-lexend text-[15px] font-medium">Title</span>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="gray" class="size-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M17.593 3.322c1.1.128 1.907 1.077 1.907 2.185V21L12 17.25 4.5 21V5.507c0-1.108.806-2.057 1.907-2.185a48.507 48.507 0 0 1 11.186 0Z" />
                            </svg>
                        </div>
                        <div class="max-w-sm space-y-3">
                            <input type="text" class="py-3 px-4 block w-full h-11 border-gray-200 rounded-lg text-[15px] font-lexend font-medium focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none placeholder:font-light" placeholder="Insert Title">
                        </div>
                    </div>

                    <div>
                        <div class="flex justify-between items-center mb-2">
                            <span class="text-gray-500 text-lexend text-[15px] font-medium">Image Url</span>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="gray" class="size-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="m2.25 15.75 5.159-5.159a2.25 2.25 0 0 1 3.182 0l5.159 5.159m-1.5-1.5 1.409-1.409a2.25 2.25 0 0 1 3.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 0 0 1.5-1.5V6a1.5 1.5 0 0 0-1.5-1.5H3.75A1.5 1.5 0 0 0 2.25 6v12a1.5 1.5 0 0 0 1.5 1.5Zm10.5-11.25h.008v.008h-.008V8.25Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z" />
                            </svg>
                        </div>
                        <div>
                            <label for="hs-trailing-button-add-on-with-leading-and-trailing" class="sr-only">Label</label>
                            <div class="flex rounded-lg shadow-sm">
                            <span class="px-4 inline-flex items-center min-w-fit rounded-s-md border border-e-0 border-gray-200 bg-gray-300 text-[15px]">
                                <span class="text-[15px] text-black">http://</span>
                            </span>
                                <input type="text" id="hs-trailing-button-add-on-with-leading-and-trailing" name="hs-trailing-button-add-on-with-leading-and-trailing"
                                       class="py-3 px-4 block w-full h-11 border-gray-200 shadow-sm rounded-r-lg text-[15px] font-lexend font-medium focus:z-10 focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none placeholder:text-[15px] placeholder:font-light"
                                       placeholder="Insert URL">
                            </div>
                        </div>
                    </div>

                    <div class="mt-9">
                        <img src="https://i.imgur.com/wvf8mlc.png" alt="Breaking Bread" class="w-[200px] h-[313px] rounded-lg shadow-lg">
                    </div>
                </div>

                <div class="border-t sm:border-t-0 sm:border-s border-gray-200"></div>

                <!-- Secondo div interno con flex verticale -->
                <div class="flex-1 flex flex-col bg-green-200 p-4 rounded-lg">
                    <span>Secondo contenuto</span>
                    <span>Altro contenuto</span>
                </div>

                <div class="border-t sm:border-t-0 sm:border-s border-gray-200"></div>

                <!-- Terzo div interno con flex verticale -->
                <div class="flex-1 flex flex-col bg-red-200 p-4 rounded-lg">
                    <span>Terzo contenuto</span>
                    <span>Altro contenuto</span>
                </div>
            </div>
        </div>

        <script src="/generated/js/preline.js"></script>
    </body>
</html>
