// Star rating
function setStars(n) {
  document.querySelectorAll('#stars span').forEach((s, i) => {
    s.classList.toggle('on', i < n);
  });
}

document.addEventListener('DOMContentLoaded', () => {
  const range = document.getElementById('priority-range');
  const label = document.getElementById('range-label');
  if (range && label) {
    range.addEventListener('input', () => { label.textContent = range.value; });
  }

  const sidebar = document.getElementById('appSidebar');
  const toggle = document.getElementById('sidebarToggle');
  const closeBtn = document.getElementById('sidebarClose');
  const overlay = document.getElementById('sidebarOverlay');

  function openSidebar() {
    document.body.classList.add('sidebar-open');
    if (toggle) toggle.setAttribute('aria-expanded', 'true');
  }

  function closeSidebar() {
    document.body.classList.remove('sidebar-open');
    if (toggle) toggle.setAttribute('aria-expanded', 'false');
  }

  function toggleSidebar() {
    if (document.body.classList.contains('sidebar-open')) {
      closeSidebar();
    } else {
      openSidebar();
    }
  }

  if (toggle) {
    toggle.setAttribute('aria-expanded', 'false');
    toggle.addEventListener('click', toggleSidebar);
  }
  if (closeBtn) closeBtn.addEventListener('click', closeSidebar);
  if (overlay) overlay.addEventListener('click', closeSidebar);

  if (sidebar) {
    sidebar.querySelectorAll('a.nav-item').forEach((link) => {
      link.addEventListener('click', () => {
        if (window.matchMedia('(max-width: 991px)').matches) {
          closeSidebar();
        }
      });
    });
  }

  window.addEventListener('resize', () => {
    if (window.matchMedia('(min-width: 992px)').matches) {
      closeSidebar();
    }
  });
});
