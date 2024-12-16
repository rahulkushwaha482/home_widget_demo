import 'package:flutter/material.dart';
import 'package:home_widget/home_widget.dart';

void main() {
  runApp(const MyApp());
  HomeWidget.registerInteractivityCallback(homeWidgetCallBack);
}

@pragma('vm:entry-point')
Future<void> homeWidgetCallBack(Uri? uri) async {
  final counterValue =
      await HomeWidget.getWidgetData('counter', defaultValue: 0);
  final newValue = counterValue! + 1;
  await HomeWidget.saveWidgetData('counter', newValue);

  await HomeWidget.updateWidget(
    name: 'ObservableWidget',
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int? _counter;

  Future<void> _incrementCounter() async {
    final counterValue =
        await HomeWidget.getWidgetData('counter', defaultValue: 0);
    final newValue = counterValue! + 1;
    await HomeWidget.saveWidgetData('counter', newValue);

    final widget = Container(
      width: 400,
      height: 200,
      color: Colors.blue,
      child: Center(
          child: Icon(
        size: 200,
        Icons.flutter_dash,
        color: Colors.white,
      )),
    );

    await HomeWidget.renderFlutterWidget(widget,
        key: 'dash', logicalSize: Size(430, 200));

    await HomeWidget.updateWidget(
      name: 'ObservableWidget',
    );

    setState(() {
      _counter = newValue;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '${_counter ?? '-'}',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
