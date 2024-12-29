@param it.unisa.hitchpicks.storage.User user

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

        @if (user != null)
            <section>
                <div class="hs-dropdown [--placement:top-left] relative inline-flex">
                    <div class="hs-dropdown-toggle flex items-end gap-x-2 ml-4 cursor-pointer">
                        <div class="text-base flex flex-col text-right leading-5 mb-2">
                            @if (user.getTag() != null)
                                <span class="font-bold">@${user.getTag()}</span>
                            @else
                                <span class="font-bold">@${user.getName()}</span>
                            @endif
                            @if (user.getAdmin())
                                <span class="font-normal">Admin</span>
                            @else
                                <span class="font-normal">User</span>
                            @endif
                        </div>

                        <span class="inline-flex items-center justify-center size-[65px] rounded-full bg-yellow-500 font-semibold text-white text-3xl border border-gray-500 leading-none">
                            @if (user.getTag() != null)
                                ${user.getTag().toUpperCase().charAt(0)}
                            @else
                                ${user.getName().toUpperCase().charAt(0)}
                            @endif
                        </span>
                    </div>

                    <div class="hs-dropdown-menu border border-gray-400 transition-[opacity,margin] duration hs-dropdown-open:opacity-100 opacity-0 hidden bg-white shadow-md rounded-lg mt-2 min-w-48 after:h-4 after:absolute after:-bottom-4 after:start-0 after:w-full before:h-4 before:absolute before:-top-4 before:start-0 before:w-full" role="menu" aria-orientation="vertical" aria-labelledby="hs-dropdown-default">
                        <div class="p-1 space-y-0.5">
                            <a class="flex items-center gap-x-3.5 py-2 px-3 rounded-lg text-sm text-gray-800 hover:bg-gray-100 focus:outline-none focus:bg-gray-100" href="/profile">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                </svg>
                                Profile
                            </a>
                            @if (user.getAdmin())
                                <a class="flex items-center gap-x-3.5 py-2 px-3 rounded-lg text-sm text-gray-800 hover:bg-gray-100 focus:outline-none focus:bg-gray-100" href="/admin/add-content">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M20.25 14.15v4.25c0 1.094-.787 2.036-1.872 2.18-2.087.277-4.216.42-6.378.42s-4.291-.143-6.378-.42c-1.085-.144-1.872-1.086-1.872-2.18v-4.25m16.5 0a2.18 2.18 0 0 0 .75-1.661V8.706c0-1.081-.768-2.015-1.837-2.175a48.114 48.114 0 0 0-3.413-.387m4.5 8.006c-.194.165-.42.295-.673.38A23.978 23.978 0 0 1 12 15.75c-2.648 0-5.195-.429-7.577-1.22a2.016 2.016 0 0 1-.673-.38m0 0A2.18 2.18 0 0 1 3 12.489V8.706c0-1.081.768-2.015 1.837-2.175a48.111 48.111 0 0 1 3.413-.387m7.5 0V5.25A2.25 2.25 0 0 0 13.5 3h-3a2.25 2.25 0 0 0-2.25 2.25v.894m7.5 0a48.667 48.667 0 0 0-7.5 0M12 12.75h.008v.008H12v-.008Z" />
                                    </svg>
                                    Admin dashboard
                                </a>
                            @endif
                            <a class="flex items-center gap-x-3.5 py-2 px-3 rounded-lg text-sm text-gray-800 hover:bg-gray-100 focus:outline-none focus:bg-gray-100" href="/logout">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 9V5.25A2.25 2.25 0 0 1 10.5 3h6a2.25 2.25 0 0 1 2.25 2.25v13.5A2.25 2.25 0 0 1 16.5 21h-6a2.25 2.25 0 0 1-2.25-2.25V15m-3 0-3-3m0 0 3-3m-3 3H15" />
                                </svg>
                                Log-out
                            </a>
                        </div>
                    </div>
                </div>
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