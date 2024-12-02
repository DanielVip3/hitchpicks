<%-- Whether the user is logged in or not (in the future, will be replaced with actual user data object) --%>
@param boolean logged

<%-- Whether the navbar is showing in admin page or not (in admin page, the logo gets hidden) --%>
@param boolean admin = false

<header class="flex h-[100px] w-full items-center px-32 font-medium ${admin ? "justify-end" : "justify-between"}">
    @if (!admin)
        <a href="/" class="cursor-pointer flex items-center gap-x-3.5">
            <img alt="Hitchpicks logo" src="/assets/Hitchpicks-Transparent-NoTitle.png" class="w-[45px] h-[45px]" />
            <h1 class="text-3xl">Hitchpicks</h1>
        </a>
    @endif

    <section class="flex items-center h-10 text-base">
        <div class="relative w-[450px] h-full mr-6">
            <label>
                <input type="text" name="search" placeholder="Search..." class="py-3 px-4 block w-full h-full border-gray-600 rounded-lg focus:border-gray-800 focus:ring-gray-800 placeholder-gray-400 placeholder:font-normal shadow-md shadow-black/25">
            </label>
            <div class="absolute inset-y-0 end-0 flex items-center pointer-events-none pe-3">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-5">
                    <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
                </svg>
            </div>
        </div>

        @if (logged)
            <section class="flex items-end gap-x-2 ml-4">
                <div class="text-base flex flex-col text-right leading-5 mb-2">
                    <span class="font-bold">@Frainaman</span>
                    <span class="font-normal">Admin</span>
                </div>

                <img alt="Default avatar" class="inline-block rounded-full size-[65px] border border-gray-400" src="/assets/avatar.png" />
            </section>
        @else
            <a href="/login" class="cursor-pointer py-3 px-4 h-full inline-flex items-center gap-x-2 rounded-lg border border-transparent text-gray-800 hover:bg-gray-100 focus:outline-none focus:bg-gray-100 disabled:opacity-50 disabled:pointer-events-none">
                Log-in
            </a>

            <a href="/signup" class="cursor-pointer py-3 px-6 h-full inline-flex items-center gap-x-2 font-normal rounded-lg border border-transparent bg-primary-600 text-white hover:bg-primary-800 focus:outline-none focus:bg-primary-800 disabled:opacity-50 disabled:pointer-events-none shadow-md shadow-black/25">
                Sign up
            </a>
        @endif
    </section>
</header>