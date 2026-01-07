// sidebar.js

const initSidebar = () => {
    const menuToggles = document.querySelectorAll(".menu-toggle");
    const subLinks = document.querySelectorAll(".sub-link");
    const currentPath = window.location.pathname;

    // ১. মেনু ক্লিক লজিক
    menuToggles.forEach(toggle => {
        // আগের ইভেন্ট রিমুভ করে নতুন করে যোগ করা (ডবল ক্লিক সমস্যা এড়াতে)
        toggle.onclick = function (e) {
            e.preventDefault();
            const targetId = this.getAttribute("data-target");
            const submenu = document.getElementById(targetId);

            // Toggle logic
            if (submenu.classList.contains("open")) {
                submenu.classList.remove("open");
                this.classList.remove("active");
                localStorage.removeItem("openSubmenu"); // স্টেট রিমুভ
            } else {
                // আপনি চাইলে একসাথে একটিই মেনু খোলা রাখতে পারেন নিচের লাইনটি ব্যবহার করে:
                // document.querySelectorAll('.submenu').forEach(s => s.classList.remove('open'));

                submenu.classList.add("open");
                this.classList.add("active");
                localStorage.setItem("openSubmenu", targetId); // স্টেট সেভ
            }
        };
    });

    // ২. পেজ লোড হওয়ার পর অটোমেটিক সাবমেনু খোলা রাখা
    subLinks.forEach(link => {
        // যদি লিঙ্কের href বর্তমান URL এর সাথে মিলে যায়
        if (link.getAttribute("href") === currentPath) {
            link.classList.add("active-link");
            const parentSubmenu = link.closest(".submenu");
            if (parentSubmenu) {
                parentSubmenu.classList.add("open");
                const toggleBtn = document.querySelector(`[data-target="${parentSubmenu.id}"]`);
                if (toggleBtn) toggleBtn.classList.add("active");
            }
        }
    });

    // ৩. লোকাল স্টোরেজ চেক (যদি ইউজার কোনো সাবলিঙ্কে ক্লিক না করে শুধু মেনু খুলে পেজ রিফ্রেশ করে)
    const savedMenuId = localStorage.getItem("openSubmenu");
    if (savedMenuId) {
        const savedMenu = document.getElementById(savedMenuId);
        const savedToggle = document.querySelector(`[data-target="${savedMenuId}"]`);
        if (savedMenu && !savedMenu.classList.contains("open")) {
            savedMenu.classList.add("open");
            if (savedToggle) savedToggle.classList.add("active");
        }
    }
};

// পেজ লোড হলে এবং ব্রাউজারের ব্যাক বাটনে ক্লিক করলেও যেন কাজ করে
document.addEventListener("DOMContentLoaded", initSidebar);
window.addEventListener("pageshow", initSidebar);

