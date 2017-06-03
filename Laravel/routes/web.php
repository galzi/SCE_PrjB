<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();

Route::get('/home', 'HomeController@index');

// Routes
Route::get('/exchange', function () {
    return view('WIDGETS.exchangerates');
});

Route::get('/history', function () {
    return view('WIDGETS.history');
});

Route::get('/rss', function () {
    return view('WIDGETS.rss');
});

Route::get('/logon', function () {
    return view('WIDGETS.logon');
});