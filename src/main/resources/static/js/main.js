/**
 * Main JavaScript file for Smart Library Management System
 * Contains utility functions and common operations
 */

// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Check if user is authenticated
 */
function checkAuth() {
    const user = localStorage.getItem('user');
    if (!user) {
        window.location.href = '/static/login.html';
        return null;
    }
    return JSON.parse(user);
}

/**
 * Logout user
 */
function logout() {
    localStorage.removeItem('user');
    window.location.href = '/static/login.html';
}

/**
 * Show alert message
 */
function showAlert(message, type = 'info', containerId = 'alertContainer') {
    const container = document.getElementById(containerId);
    if (!container) return;

    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.role = 'alert';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    container.innerHTML = '';
    container.appendChild(alertDiv);

    // Auto dismiss after 5 seconds
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

/**
 * Format date to readable format
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

/**
 * Calculate days between two dates
 */
function daysBetween(date1, date2) {
    const oneDay = 24 * 60 * 60 * 1000;
    const firstDate = new Date(date1);
    const secondDate = new Date(date2);
    return Math.round(Math.abs((firstDate - secondDate) / oneDay));
}

/**
 * Check if date is overdue
 */
function isOverdue(dueDate) {
    const today = new Date();
    const due = new Date(dueDate);
    return today > due;
}

/**
 * Format currency
 */
function formatCurrency(amount) {
    return `₹${parseFloat(amount).toFixed(2)}`;
}

/**
 * Validate email format
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Validate form fields
 */
function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form) return false;

    const inputs = form.querySelectorAll('input[required], select[required], textarea[required]');
    let isValid = true;

    inputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('is-invalid');
            isValid = false;
        } else {
            input.classList.remove('is-invalid');
            input.classList.add('is-valid');
        }
    });

    return isValid;
}

/**
 * Clear form validation
 */
function clearFormValidation(formId) {
    const form = document.getElementById(formId);
    if (!form) return;

    const inputs = form.querySelectorAll('input, select, textarea');
    inputs.forEach(input => {
        input.classList.remove('is-invalid', 'is-valid');
    });
}

/**
 * Show loading spinner
 */
function showLoading(elementId) {
    const element = document.getElementById(elementId);
    if (!element) return;

    element.innerHTML = `
        <div class="text-center py-5">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
            <p class="mt-3">Loading...</p>
        </div>
    `;
}

/**
 * Show error message
 */
function showError(elementId, message) {
    const element = document.getElementById(elementId);
    if (!element) return;

    element.innerHTML = `
        <div class="alert alert-danger" role="alert">
            <i class="fas fa-exclamation-triangle"></i> ${message}
        </div>
    `;
}

/**
 * Show empty state
 */
function showEmptyState(elementId, message, icon = 'inbox') {
    const element = document.getElementById(elementId);
    if (!element) return;

    element.innerHTML = `
        <div class="text-center py-5 text-muted">
            <i class="fas fa-${icon} fa-4x mb-3"></i>
            <p class="lead">${message}</p>
        </div>
    `;
}

/**
 * Debounce function for search
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

/**
 * Copy to clipboard
 */
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        showAlert('Copied to clipboard!', 'success');
    }).catch(err => {
        console.error('Failed to copy:', err);
    });
}

/**
 * Confirm action
 */
function confirmAction(message) {
    return confirm(message);
}

/**
 * Get user role badge HTML
 */
function getRoleBadge(role) {
    const badgeClass = role === 'ADMIN' ? 'bg-danger' : 'bg-primary';
    return `<span class="badge ${badgeClass}">${role}</span>`;
}

/**
 * Get status badge HTML
 */
function getStatusBadge(status) {
    const badgeClass = status === 'ISSUED' ? 'bg-warning' : 'bg-success';
    return `<span class="badge ${badgeClass}">${status}</span>`;
}

/**
 * Truncate text
 */
function truncateText(text, maxLength) {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
}

/**
 * Initialize tooltips (Bootstrap)
 */
function initTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

/**
 * Initialize popovers (Bootstrap)
 */
function initPopovers() {
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
}

/**
 * Handle API errors
 */
function handleApiError(error, defaultMessage = 'An error occurred') {
    console.error('API Error:', error);
    
    if (error.response) {
        // Server responded with error
        return error.response.data.message || defaultMessage;
    } else if (error.request) {
        // Request made but no response
        return 'Server is not responding. Please try again later.';
    } else {
        // Something else happened
        return defaultMessage;
    }
}

/**
 * Format book availability
 */
function formatAvailability(available, total) {
    const percentage = (available / total) * 100;
    let badgeClass = 'bg-success';
    
    if (percentage < 25) {
        badgeClass = 'bg-danger';
    } else if (percentage < 50) {
        badgeClass = 'bg-warning';
    }
    
    return `<span class="badge ${badgeClass}">${available} / ${total}</span>`;
}

/**
 * Initialize page
 */
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Bootstrap components
    initTooltips();
    initPopovers();
    
    // Add smooth scrolling
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
});

/**
 * Export functions for use in other scripts
 */
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        checkAuth,
        logout,
        showAlert,
        formatDate,
        daysBetween,
        isOverdue,
        formatCurrency,
        isValidEmail,
        validateForm,
        clearFormValidation,
        showLoading,
        showError,
        showEmptyState,
        debounce,
        copyToClipboard,
        confirmAction,
        getRoleBadge,
        getStatusBadge,
        truncateText,
        handleApiError,
        formatAvailability
    };
}
